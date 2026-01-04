package com.bronx.crm.domain.security.auth.service.authentication;

import com.bronx.crm.domain.security.auth.dto.*;
import com.bronx.crm.domain.security.otp.dto.ForgotPasswordRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
//    AuthResponse refreshToken(String Token);
    void forgotPassword(ForgetPasswordRequest request);
    void resetPassword(ResetPasswordRequest request);
    void logout(String refreshToken);
    boolean verifyOtp(String phone,String otp);
}
