package com.bronx.crm.application.auth.otp.service;

import com.bronx.crm.domain.identity.enumz.DeliveryChannel;
import com.bronx.crm.domain.identity.enumz.OtpPurpose;

public interface OtpService {
//     String generateAndSendOtpViaEmail(String email, OtpPurpose purpose);
    String generateAndSendOtpViaSms(String phoneNumber, OtpPurpose purpose);
//    String generateAndSendOtpViaBoth(String email, String phoneNumber, OtpPurpose purpose);
    boolean verifyOtp(String recipient, String otpCode,
                      OtpPurpose purpose);
}
