package com.bronx.crm.domain.identity.user.entity;

import com.bronx.crm.common.audit.BaseEntity;
import com.bronx.crm.domain.identity.permission.entity.Permission;
import com.bronx.crm.domain.identity.role.entity.Role;
import com.bronx.crm.domain.organization.company.entity.Company;
import com.bronx.crm.domain.organization.department.entity.Department;
import com.bronx.crm.domain.organization.division.entity.Division;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "user", indexes = {@Index(name = "idx_user_username", columnList = "username")})
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

//    // NEW: Country code for international phone numbers
//    @Column(name = "country_code", length = 5)
//    private String countryCode;

    // NEW: Phone verification status
    @Column(name = "phone_verified", nullable = false)
    private Boolean phoneVerified = false;

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

    @Column(name = "status",length = 20)
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "last_login", nullable = false)
    private LocalDate lastLogin;

    @Column(name = "login_attempt", nullable = false)
    private Short loginAttempt = 0;

    @Column(name = "account_non_expired")
    @Builder.Default
    private boolean accountExpired = false;

    @Column(name = "account_non_locked")
    @Builder.Default
    private boolean accountLocked = false;

    @Column(name = "credentials_non_expired")
    @Builder.Default
    private boolean credentialsExpired = false;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private UserProfile profile;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"),
            indexes = {@Index(columnList = "user_id"), @Index(columnList = "role_id")})
    private Set<Role> roles = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_permissions",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id"),
            indexes = {@Index(columnList = "user_id"), @Index(columnList = "permission_id")})
    private Set<Permission> permissions = new HashSet<>();


    public enum UserStatus{
        INACTIVE,ACTIVE
    }

    public boolean isEnabled() {
        return status == UserStatus.ACTIVE;
    }

}