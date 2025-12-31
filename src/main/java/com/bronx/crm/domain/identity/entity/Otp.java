package com.bronx.crm.domain.identity.entity;

import com.bronx.crm.common.audit.BaseEntity;
import com.bronx.crm.domain.identity.enumz.DeliveryChannel;
import com.bronx.crm.domain.identity.enumz.OtpPurpose;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
@Entity
@Table(name = "otp")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Otp extends BaseEntity<Long> {

    // Can be email or phone
    @Column(name = "recipient", nullable = false)
    private String recipient;

    // NEW: Delivery channel
    @Column(name = "channel", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private DeliveryChannel channel=DeliveryChannel.SMS;

    @Column(name = "otp_code", nullable = false, length = 6)
    private String otpCode;

    @Column(name = "purpose", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private OtpPurpose purpose;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = "is_used", nullable = false)
    private Boolean isUsed = false;

    @Column(name = "used_at")
    private LocalDateTime usedAt;

    @Column(name = "attempts", nullable = false)
    private Integer attempts = 0;


    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }

    public boolean isValid() {
        return !isUsed && !isExpired() && attempts < 3;
    }
}