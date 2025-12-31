package com.bronx.crm.application.auth.otp.service;

import com.bronx.crm.domain.identity.enumz.OtpPurpose;

public interface SmsService {
    void sendOtpSms(String phoneNumber, String otpCode, OtpPurpose purpose);

}
