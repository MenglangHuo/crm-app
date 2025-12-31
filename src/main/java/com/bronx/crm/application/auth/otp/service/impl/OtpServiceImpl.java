package com.bronx.crm.application.auth.otp.service.impl;

import com.bronx.crm.application.auth.otp.repository.OtpRepository;
import com.bronx.crm.application.auth.otp.service.OtpService;
import com.bronx.crm.application.auth.otp.service.SmsService;
import com.bronx.crm.common.exception.BadRequestException;
import com.bronx.crm.domain.identity.entity.Otp;
import com.bronx.crm.domain.identity.enumz.DeliveryChannel;
import com.bronx.crm.domain.identity.enumz.OtpPurpose;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {

    private final OtpRepository otpRepository;
    private final SmsService smsService;

    @Value("${otp.length:6}")
    private int otpLength;

    @Value("${otp.expiry-minutes}")
    private int optExpire;

    @Value("${otp.max-attempts:3}")
    private int maxAttempt;

    @Override
    public String generateAndSendOtpViaSms(String phoneNumber, OtpPurpose purpose) {
        return generateAndSendOtp(phoneNumber, purpose, DeliveryChannel.SMS);
    }


    @Override
    public boolean verifyOtp(String recipient, String otpCode, OtpPurpose purpose) {
        Optional<Otp> otpOptional = otpRepository.findByRecipientAndOtpCodeAndPurposeAndChannel(
                recipient, otpCode, purpose, DeliveryChannel.SMS
        );
        if (otpOptional.isEmpty()) {
            throw new BadRequestException("Invalid OTP code");
        }
        Otp otp = otpOptional.get();
        if (otp.isExpired()) {
            throw new BadRequestException("OTP has expired. Please request a new one.");
        }

        if (otp.getIsUsed()) {
            throw new BadRequestException("OTP has already been used");
        }
        if (otp.getAttempts() >= maxAttempt) {
            throw new BadRequestException("Maximum verification attempts exceeded");
        }

        if (!otp.getOtpCode().equals(otpCode)) {
            // Increment attempts
            otp.setAttempts(otp.getAttempts() + 1);
            otpRepository.save(otp);
            throw new BadRequestException(
                    String.format("Invalid OTP. %d attempts remaining",
                            maxAttempt - otp.getAttempts())
            );
        }
        // Mark as used
        otp.setIsUsed(true);
        otp.setUsedAt(LocalDateTime.now());
        otpRepository.save(otp);
        return true;
    }

    private String generateAndSendOtp(String recipient, OtpPurpose purpose, DeliveryChannel channel){
        // Check if there's a recent valid OTP
        Optional<Otp> existingOtp = otpRepository.findLatestValidOtp(
                recipient, purpose, channel, LocalDateTime.now()
        );

        if (existingOtp.isPresent()) {
            Otp otp = existingOtp.get();
            if (otp.getCreatedAt().plusMinutes(1).isAfter(LocalDateTime.now())) {
                throw new BadRequestException(
                        "Please wait before requesting a new OTP. Try again in 1 minute."
                );
            }
        }

        String otpCode = generateOtpCode();
            // Send via appropriate channel
        sendOtpViaChannel(recipient, otpCode, purpose, channel);
        // Store OTP
        storeOtp(recipient, otpCode, purpose, channel);

        log.info("OTP generated for recipient via {}: purpose={}", channel, purpose);
        return otpCode;

    }

    private void storeOtp(String recipient, String otpCode,
                          OtpPurpose purpose, DeliveryChannel channel) {
        Otp otp = Otp.builder()
                .recipient(recipient)
                .channel(channel)
                .otpCode(otpCode)
                .purpose(purpose)
                .expiresAt(LocalDateTime.now().plusMinutes(optExpire))
                .isUsed(false)
                .attempts(0)
                .build();

        otpRepository.save(otp);
    }

    private String generateOtpCode() {
        SecureRandom random = new SecureRandom();
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < otpLength; i++) {
            otp.append(random.nextInt(10));
        }
        return otp.toString();
    }

    @Transactional
    public void cleanupExpiredOtps() {
        otpRepository.deleteByExpiresAtBefore(LocalDateTime.now());
    }
    private void sendOtpViaChannel(String recipient, String otpCode,
                                   OtpPurpose purpose, DeliveryChannel channel) {
        switch (channel) {
            case SMS -> smsService.sendOtpSms(recipient, otpCode, purpose);
            case BOTH -> {
                // This case is handled separately in generateAndSendOtpViaBoth
            }
        }
    }
}
