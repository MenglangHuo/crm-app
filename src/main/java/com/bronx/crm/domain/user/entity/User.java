package com.bronx.crm.domain.user.entity;

import com.bronx.crm.common.audit.BaseEntity;
import com.bronx.crm.domain.company.entity.Company;
import com.bronx.crm.domain.organization.entity.Department;
import com.bronx.crm.domain.organization.entity.Division;
import com.bronx.crm.domain.identity.entity.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class User extends BaseEntity<Long> {

    @Column(name = "unique_key", nullable = false, unique = true)
    private UUID uniqueKey;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "email")
    private String email;

    // NEW: Phone number field
    @Column(name = "phone", length = 20)
    private String phone;

    // NEW: Country code for international phone numbers
    @Column(name = "country_code", length = 5)
    private String countryCode;

    // NEW: Phone verification status
    @Column(name = "phone_verified", nullable = false)
    private Boolean phoneVerified = false;

    // NEW: Email verification status
    @Column(name = "email_verified", nullable = false)
    private Boolean emailVerified = false;

    @Column(name = "attributes", columnDefinition = "jsonb")
    private String attributes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "division_id")
    private Division division;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "last_login", nullable = false)
    private LocalDate lastLogin;

    @Column(name = "login_attempt", nullable = false)
    private Short loginAttempt = 0;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private UserProfile profile;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<UserRole> userRoles = new HashSet<>();

    public String getFullPhoneNumber() {
        if (phone == null) return null;
        if (countryCode == null) return phone;
        return countryCode + phone;
    }
}