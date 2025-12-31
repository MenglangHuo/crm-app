package com.bronx.crm.domain.employee.entity;
import com.bronx.crm.common.audit.BaseEntity;
import com.bronx.crm.domain.company.entity.Company;
import com.bronx.crm.domain.organization.entity.Department;
import com.bronx.crm.domain.organization.entity.Division;
import jakarta.persistence.*;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import java.time.LocalDate;

@Entity
@Table(name = "employee_profile")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE employee_profile SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@Where(clause = "deleted_at IS NULL")
public class EmployeeProfile extends BaseEntity<Long> {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false, unique = true)
    private Employee employee;

    @Column(name = "image")
    private String image;

    @Column(name = "bio")
    private String bio;

    @Column(name = "address")
    private String address;

    @Column(name = "dob")
    private LocalDate dob;


}