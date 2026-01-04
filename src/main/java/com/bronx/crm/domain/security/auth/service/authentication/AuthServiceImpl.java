package com.bronx.crm.domain.security.auth.service.authentication;
import com.bronx.crm.common.exception.BadRequestException;
import com.bronx.crm.common.exception.ResourceNotFoundException;
import com.bronx.crm.common.exception.UnauthorizedException;
import com.bronx.crm.domain.identity.enumz.OtpPurpose;
import com.bronx.crm.domain.identity.role.entity.Role;
import com.bronx.crm.domain.identity.role.repository.RoleRepository;
import com.bronx.crm.domain.identity.user.entity.User;
import com.bronx.crm.domain.identity.user.mapper.UserMapper;
import com.bronx.crm.domain.identity.user.repository.UserRepository;
import com.bronx.crm.domain.security.auth.dto.*;
import com.bronx.crm.domain.security.auth.service.userDetails.CustomUserDetails;
import com.bronx.crm.domain.security.jwt.service.JwtService;
import com.bronx.crm.domain.security.otp.service.OtpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.HashSet;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
//    private final RefreshTokenService refreshTokenService;
    private final OtpService otpService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;

    @Value("${auth.default-role:USER}")
    private String defaultRoleName;
    @Value("${auth.max-login-attempts:5}")
    private int maxLoginAttempts;
    @Value("${auth.lockout-duration-minutes:30}")
    private int lockoutDurationMinutes;

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        validateUniqueFields(request);
        // Create user
        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Roles
        Role defaultRole = roleRepository.findActiveByName(defaultRoleName)
                .orElseGet(this::createDefaultRole);

        if (user.getRoles() == null) {
            user.setRoles(new HashSet<>());
        }
        user.getRoles().add(defaultRole);
        user = userRepository.save(user);
        // Generate Stateless Tokens
        CustomUserDetails userDetails = new CustomUserDetails(user);
        String accessToken = jwtService.generateAccessToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);
        return buildAuthResponse(user, accessToken, refreshToken);
    }

    @Override
    @Transactional
    public AuthResponse login(LoginRequest request) {
        log.info("Processing login for username: {}", request.username());
        User user = userRepository.findByUsernameOrPhone(request.username())
                .orElseThrow(()->new BadRequestException("Invalid Credential!"));

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.username(),
                            request.password()
                    )
            );

            if (!passwordEncoder.matches(user.getPassword(), request.password())) {
                user.setLoginAttempt((short) (user.getLoginAttempt()+1));
                user.setLastLogin(LocalDate.now());
                userRepository.save(user);
                throw new UsernameNotFoundException("Invalid Credential.");
            }

            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            // Refetch to get roles/permissions for response if needed
            User fullUser = userRepository.findByUsernameWithRolesAndPermissions(userDetails.getUsername())
                    .orElseThrow(() -> new ResourceNotFoundException("User", "username", userDetails.getUsername()));

            // Stateless Tokens
            String accessToken = jwtService.generateAccessToken(userDetails);
            String refreshToken = jwtService.generateRefreshToken(userDetails);
            return buildAuthResponse(fullUser, accessToken, refreshToken);
        } catch (BadCredentialsException e) {
            handleFailedLogin(user);
            throw new UnauthorizedException("Invalid Credential");
        } catch (DisabledException e) {
            throw new UnauthorizedException("Account is disabled");
        } catch (LockedException e) {
            throw new UnauthorizedException("Account Was Lock");
        }
    }



    @Override
    @Transactional
    public void forgotPassword(ForgetPasswordRequest request) {
        log.info("Processing forgot password for phone: {}", maskPhone(request.phone()));
        String fullPhoneNumber =  request.phone();
        // Verify user exists with this phone number
        User user = userRepository.findByPhone(request.phone())
                .orElseThrow(() -> new ResourceNotFoundException("User", "phone", request.phone()));
        if (!user.getStatus().equals(User.UserStatus.INACTIVE)) {
            throw new BadRequestException("Account is disabled. Please contact support.");
        }
        // Generate and send OTP
        otpService.generateAndSendOtpViaSms(fullPhoneNumber, OtpPurpose.PASSWORD_RESET);
        log.info("OTP sent for password reset to phone: {}", maskPhone(request.phone()));
    }


    @Override
    @Transactional
    public void resetPassword(ResetPasswordRequest request) {
        log.info("Processing password reset for phone: {}", maskPhone(request.getPhone()));
        // Validate password match
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new BadRequestException("Passwords do not match");
        }

        // Verify OTP
//        otpService.verifyOtp(request.getPhone(), request.getOtpCode(), OtpPurpose.PASSWORD_RESET);
        // Find user and update password
        User user = userRepository.findByPhone(request.getPhone())
                .orElseThrow(() -> new ResourceNotFoundException("User", "phone", request.getPhone()));
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        // Reset login attempts and unlock account
        user.setLoginAttempt((short) 0);
        user.setAccountLocked(false);
        userRepository.save(user);
        // Revoke all existing refresh tokens for security
//        refreshTokenService.revokeAllUserTokens(user);
        log.info("Password reset successfully for user: {}", user.getUsername());
    }


    @Override
    @Transactional
    public void logout(String refreshToken) {
        log.info("Processing logout");
//        refreshTokenService.revokeToken(refreshToken);
        log.info("User logged out successfully");
    }

    @Override
    public boolean verifyOtp(String phone, String otp) {
        return otpService.verifyOtp(phone,otp,OtpPurpose.PHONE_VERIFICATION);
    }

    private void validateUniqueFields(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BadRequestException("Username is already taken");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email is already registered");
        }
        if (userRepository.existsByPhone(request.getPhone())) {
            throw new BadRequestException("Phone number is already registered");
        }
    }

    private void handleFailedLogin(User user) {
        user.setLoginAttempt((short) (user.getLoginAttempt() + 1));
        if (user.getLoginAttempt() >= maxLoginAttempts) {
            user.setAccountLocked(true);
            log.warn("Account locked due to {} failed attempts: {}", maxLoginAttempts, user.getUsername());
        }
        userRepository.save(user);
    }

    private Role createDefaultRole() {
        log.info("Creating default role: {}", defaultRoleName);
        Role role = Role.builder()
                .name(defaultRoleName)
                .isActive(true)
                .note("Default user role")
                .build();
        return roleRepository.save(role);
    }

    private AuthResponse buildAuthResponse(User user, String accessToken, String refreshToken) {
        UserDto userDto = userMapper.toDto(user);
        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresIn(jwtService.getAccessTokenExpirationMs() / 1000) // in seconds
                .refreshExpiresIn(jwtService.getRefreshTokenExpirationMs() / 1000)
                .user(userDto)
                .build();
    }

    private String maskPhone(String phone) {
        if (phone == null || phone.length() < 4) {
            return "****";
        }
        return "*".repeat(phone.length() - 4) + phone.substring(phone.length() - 4);
    }
}

