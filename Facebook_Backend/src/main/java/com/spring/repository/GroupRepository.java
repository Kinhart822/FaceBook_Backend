package com.spring.repository;

import com.spring.entities.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Integer> {
}

