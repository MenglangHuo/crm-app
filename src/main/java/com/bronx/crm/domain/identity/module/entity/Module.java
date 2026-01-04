package com.bronx.crm.domain.identity.module.entity;

import com.bronx.crm.common.audit.BaseEntity;
import com.bronx.crm.domain.company.entity.Company;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "module")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Module extends BaseEntity<Long> {

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(length = 100, name = "description")
    private String description;
}
