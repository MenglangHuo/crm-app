package com.bronx.crm.domain.identity.action.entity;
import com.bronx.crm.domain.identity.permission.entity.Permission;
import com.bronx.crm.domain.organization.company.entity.Company;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "action")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Action {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id",nullable = false)
    private Company company;

    @ManyToMany(mappedBy = "actions")
    @ToString.Exclude
    @JsonIgnore
    private Set<Permission> permissions = new HashSet<>();
}