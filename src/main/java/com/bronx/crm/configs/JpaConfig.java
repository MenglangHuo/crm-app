package com.bronx.crm.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import java.util.Optional;
/**
 * JPA Configuration for auditing support
 */
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaConfig {
    /**
     * Provides the current auditor (user ID) for entity auditing.
     * In a real application, this would retrieve the user from SecurityContext.
     *
     * @return AuditorAware bean that provides the current user ID
     */
    @Bean
    public AuditorAware<Long> auditorProvider() {
        // TODO: Replace with actual implementation that gets user from SecurityContext
        // Example: SecurityContextHolder.getContext().getAuthentication().getPrincipal()
        return () -> Optional.of(1L); // Default to system user
    }
}

