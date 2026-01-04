package com.bronx.crm.domain.security.otp.repository;

import com.bronx.crm.domain.security.otp.entity.Otp;
import com.bronx.crm.domain.identity.enumz.DeliveryChannel;
import com.bronx.crm.domain.identity.enumz.OtpPurpose;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Long> {

    @Query("SELECT o FROM Otp o WHERE o.recipient = :recipient AND o.purpose = :purpose " +
            "AND o.channel = :channel AND o.isUsed = false AND o.expiresAt > :now ORDER BY o.createdAt DESC")
    Optional<Otp> findLatestValidOtp(@Param("recipient") String recipient,
                                     @Param("purpose") OtpPurpose purpose,
                                     @Param("channel") DeliveryChannel channel,
                                     @Param("now") LocalDateTime now);

    @Query("SELECT o FROM Otp o WHERE o.recipient = :recipient AND o.otpCode = :otpCode " +
            "AND o.purpose = :purpose AND o.channel = :channel AND o.isUsed = false")
    Optional<Otp> findByRecipientAndOtpCodeAndPurposeAndChannel(
            @Param("recipient") String recipient,
            @Param("otpCode") String otpCode,
            @Param("purpose") OtpPurpose purpose,
            @Param("channel") DeliveryChannel channel);

    void deleteByExpiresAtBefore(LocalDateTime dateTime);
}
