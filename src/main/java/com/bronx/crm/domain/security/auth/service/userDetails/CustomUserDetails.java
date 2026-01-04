package com.bronx.crm.domain.security.auth.service.userDetails;

import com.bronx.crm.domain.identity.permission.entity.Permission;
import com.bronx.crm.domain.identity.user.entity.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter

public class CustomUserDetails implements UserDetails {

    private final Long id;
    private final String username;
    private final String password;
    private final String email;
    private final String firstname;
    private final String lastname;
    private final boolean enabled;
    private final boolean accountNonLocked;
    private final boolean accountNonExpired;
    private final boolean credentialsNonExpired;
    private final Collection<? extends GrantedAuthority> authorities;


    public CustomUserDetails(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.enabled = user.getStatus() == User.UserStatus.ACTIVE;
        this.accountNonLocked = !user.isAccountLocked();
        this.accountNonExpired = !user.isAccountExpired();
        this.credentialsNonExpired = !user.isCredentialsExpired();
        this.authorities = extractAuthorities(user);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    private Collection<? extends GrantedAuthority> extractAuthorities(User user) {
        // extract role-base authorities
        Set<GrantedAuthority> roleAuthorities = user.getRoles().stream()
                .filter(role -> role != null && role.getIsActive())
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName().toUpperCase()))
                .collect(Collectors.toSet());
        // extract permission-base logic as requested
        Set<GrantedAuthority> permissionAuthorities = user.getRoles().stream()
                .filter(role -> role != null && role.getIsActive())
                .flatMap(role -> role.getPermissions().stream())
                .flatMap(permission -> permission.getActions().stream()
                        .map(action -> new SimpleGrantedAuthority(permission.getName() + ":" + action.getName())))
                .collect(Collectors.toSet());
        roleAuthorities.addAll(permissionAuthorities);
        return roleAuthorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
