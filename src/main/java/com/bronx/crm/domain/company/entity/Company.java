package com.bronx.crm.domain.company.entity;

import com.bronx.crm.common.audit.BaseEntity;
import com.bronx.crm.domain.location.entity.Location;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "company")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE company SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@Where(clause = "deleted_at IS NULL")
public class Company extends BaseEntity<Long> {
    @Column(name = "name", nullable = false,length = 100)
    private String name;

    @Column(name = "note")
    private String note;

    @Column(name = "address")
    private String address;


    @Column(name = "image_url")
    private String imageUrl;

}
