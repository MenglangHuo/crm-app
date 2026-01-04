package com.bronx.crm.common.utils.security;

import com.bronx.crm.domain.security.auth.service.userDetails.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

    /**
     * Get current authenticated user
     */
    public static CustomUserDetails getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            return (CustomUserDetails) authentication.getPrincipal();
        }
        return null;
    }

    /**
     * Get current user ID
     */
    public static Long getCurrentUserId() {
        CustomUserDetails user = getCurrentUser();
        return user != null ? user.getId() : null;
    }

    /**
     * Get current username
     */
    public static String getCurrentUsername() {
        CustomUserDetails user = getCurrentUser();
        return user != null ? user.getUsername() : null;
    }

    /**
     * Check if current user has a specific role
     */
    public static boolean hasRole(String role) {
        CustomUserDetails user = getCurrentUser();
        if (user == null) {
            return false;
        }

        String roleWithPrefix = role.startsWith("ROLE_") ? role : "ROLE_" + role;
        return user.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals(roleWithPrefix));
    }

    /**
     * Check if current user has a specific permission
     */
    public static boolean hasPermission(String permission) {
        CustomUserDetails user = getCurrentUser();
        if (user == null) {
            return false;
        }

        return user.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals(permission));
    }

    /**
     * Check if current user has a specific permission with action
     */
    public static boolean hasPermission(String permissionName, String action) {
        return hasPermission(permissionName + ":" + action);
    }

    /**
     * Check if current user has any of the specified roles
     */
    public static boolean hasAnyRole(String... roles) {
        for (String role : roles) {
            if (hasRole(role)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if current user has any of the specified permissions
     */
    public static boolean hasAnyPermission(String... permissions) {
        for (String permission : permissions) {
            if (hasPermission(permission)) {
                return true;
            }
        }
        return false;
    }
}
