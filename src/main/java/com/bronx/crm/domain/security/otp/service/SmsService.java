package com.bronx.crm.domain.security.otp.service;

import com.bronx.crm.domain.identity.enumz.OtpPurpose;

public interface SmsService {
    void sendOtpSms(String phoneNumber, String otpCode, OtpPurpose purpose);

}
