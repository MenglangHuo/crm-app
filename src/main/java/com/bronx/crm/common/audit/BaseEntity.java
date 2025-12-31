package com.bronx.crm.common.audit;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.io.Serializable;
import java.time.LocalDateTime;
/**
 * Abstract base class for auditable entities.
 * Provides common audit fields: id, createdAt, createdBy, updatedAt, updatedBy, deletedAt
 *
 * @param <T> The type of the entity ID (typically Long or UUID)
 */
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity<T extends Serializable> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private T id;
    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    @CreatedBy
    @Column(name = "created_by", updatable = false)
    private Long createdBy;
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @LastModifiedBy
    @Column(name = "updated_by")
    private Long updatedBy;
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
    /**
     * Check if the entity is soft deleted
     */
    public boolean isDeleted() {
        return deletedAt != null;
    }
    /**
     * Soft delete the entity by setting deletedAt timestamp
     */
    public void softDelete() {
        this.deletedAt = LocalDateTime.now();
    }
    /**
     * Restore a soft deleted entity
     */
    public void restore() {
        this.deletedAt = null;
    }
    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        updatedAt = LocalDateTime.now();
    }
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

