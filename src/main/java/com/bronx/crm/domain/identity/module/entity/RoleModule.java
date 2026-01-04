package com.bronx.crm.domain.identity.module.entity;

import com.bronx.crm.domain.identity.action.entity.Action;
import com.bronx.crm.domain.identity.role.entity.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "role_module")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleModule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id")
    private Action.Module module;
}