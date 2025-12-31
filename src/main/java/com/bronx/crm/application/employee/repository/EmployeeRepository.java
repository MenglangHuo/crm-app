package com.bronx.crm.application.employee.repository;

import com.bronx.crm.domain.employee.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    boolean existsByEmail(String email);

    @Query("select (count(e) > 0) from Employee e where e.email = ?1 and e.id=?2")
    boolean existsByEmail(String email,Long id);

    boolean existsByPhone(String phoneNumber);

    @Query("select (count(e) > 0) from Employee e where e.phone = ?1 and e.id=?2")
    boolean existsByPhone(String phoneNumber,Long id);

    boolean existsByUsername(String username);



    @Query("select e from Employee e where e.username = ?1 and e.deletedAt is null")
    Optional<Employee> findByUsername(String username);

    @Query("select e from Employee e where e.email = ?1 and e.deletedAt is null")
    Optional<Employee> findByEmail(String email);

    @Query("select e from Employee e where e.phone = ?1 and e.deletedAt is null")
    Optional<Employee> findByPhone(String email);

    @Query("select e from Employee e where e.uniqueKey = ?1 and e.deletedAt is null")
    Optional<Employee> findByUniqueKey(String uniqueKey);


    @Query("SELECT e FROM Employee e WHERE " +
            "(:companyId IS NULL OR e.company.id = :companyId) AND " +
            "(:divisionId IS NULL OR e.division.id = :divisionId) AND " +
            "(:departmentId IS NULL OR e.department.id = :departmentId) AND " +
            "e.deletedAt IS NULL")
    Page<Employee> findByFilters(@Param("companyId") Long companyId,
                                 @Param("divisionId") Long divisionId,
                                 @Param("departmentId") Long departmentId,
                                 Pageable pageable);

    @Query("""
    SELECT e
    FROM Employee e
    WHERE e.company.id = :companyId
      AND (
            :search IS NULL OR :search = ''
         OR LOWER(e.firstname) LIKE LOWER(CONCAT('%', :search, '%'))
         OR LOWER(e.lastname)  LIKE LOWER(CONCAT('%', :search, '%'))
         OR LOWER(e.email)     LIKE LOWER(CONCAT('%', :search, '%'))
         OR LOWER(e.username)  LIKE LOWER(CONCAT('%', :search, '%'))
      )
      AND e.deletedAt IS NULL
""")

    Page<Employee> search(
            @Param("companyId") Long companyId,
            @Param("search") String search,
            Pageable pageable
    );



    @Modifying
    @Query("UPDATE Employee e SET e.deletedAt = :deletedAt WHERE e.id = :id")
    void softDelete(@Param("id") Long id, @Param("deletedAt") LocalDateTime deletedAt);

//    @Modifying
//    @Query("UPDATE Employee e SET e.loginAttempt = :attempts WHERE e.id = :id")
//    void updateLoginAttempts(@Param("id") Long id, @Param("attempts") Integer attempts);
}
