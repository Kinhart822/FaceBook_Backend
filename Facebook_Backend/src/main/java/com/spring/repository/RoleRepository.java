package com.spring.repository;

import com.spring.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    @Query("SELECT r FROM Role r WHERE r.name = :title")
    Role findByTitle(@Param("title") String title);
}
