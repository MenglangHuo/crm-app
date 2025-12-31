package com.bronx.crm.application.user.repository;

import com.bronx.crm.domain.user.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    @Query("select (count(e) > 0) from UserProfile e where e.user.id = ?1")
    boolean existsByUserId(Long userId);

    @Query("select e from UserProfile e where e.user.id = ?1")
    Optional<UserProfile> findByUserId(Long userId);
}
