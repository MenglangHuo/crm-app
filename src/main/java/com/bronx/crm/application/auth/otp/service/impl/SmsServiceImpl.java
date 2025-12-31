package com.bronx.crm.application.auth.otp.service.impl;

import com.bronx.crm.application.auth.otp.service.SmsService;
import com.bronx.crm.domain.identity.enumz.OtpPurpose;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SmsServiceImpl implements SmsService {

    @Value("${sms.enabled:false}")
    private boolean smsEnabled;

    @Value("${sms.provider:TWILIO}")
    private String smsProvider;

    @Override
    public void sendOtpSms(String phoneNumber, String otpCode, OtpPurpose purpose) {
        String message = buildSmsMessage(otpCode, purpose);

        if (!smsEnabled) {
            log.info("=================================================");
            log.info("SMS sending is DISABLED. Would send to: {}", phoneNumber);
            log.info("OTP Code: {}", otpCode);
            log.info("Message: {}", message);
            log.info("=================================================");
            return;
        }
        try {
            // TODO: Implement actual SMS sending based on provider
            switch (smsProvider.toUpperCase()) {
                case "TWILIO":
                    sendViaTwilio(phoneNumber, message);
                    break;
                case "PLUS_GATE":
                    sendViaPlusGate(phoneNumber,message);
                    break;
                default:
                    sendViaTelegramChannel(phoneNumber, message);
                    log.warn("Unknown SMS provider: {}", smsProvider);
            }

            log.info("SMS sent successfully to: {}", maskPhoneNumber(phoneNumber));
        } catch (Exception e) {
            log.error("Failed to send SMS to: {}", maskPhoneNumber(phoneNumber), e);
            throw new RuntimeException("Failed to send SMS", e);
        }
        log.info("SMS sent successfully to: {}", maskPhoneNumber(phoneNumber));
    }

    private void sendViaTwilio(String phoneNumber, String message) {
        // TODO: Implement Twilio integration
        log.info("Sending SMS via Twilio to: {}", maskPhoneNumber(phoneNumber));
    }
    private void sendViaPlusGate(String phoneNumber, String message) {
        // TODO: Implement PlusGate integration
        log.info("Sending SMS via plus gate to: {}", maskPhoneNumber(phoneNumber));
    }
    private void sendViaTelegramChannel(String phoneNumber,String message){
        log.info("Sending SMS via plus gate to: {}", maskPhoneNumber(phoneNumber));
    }
    private String buildSmsMessage(String otpCode, OtpPurpose purpose) {
        String purposeText = switch (purpose) {
            case REGISTRATION -> "complete your registration";
            case PASSWORD_RESET -> "reset your password";
            case PHONE_VERIFICATION -> "verify your phone number";
            case LOGIN_2FA -> "complete your login";
            default -> "verify your identity";
        };

        return String.format("Your OTP code to %s is: %s. Valid for 1 minutes. Do not share this code.",
                purposeText, otpCode);
    }

    private String maskPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.length() < 4) {
            return "****";
        }
        return "*".repeat(phoneNumber.length() - 4) + phoneNumber.substring(phoneNumber.length() - 4);
    }
}
