package com.spring.repository;

import com.spring.entities.UserAbout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAboutRepository extends JpaRepository<UserAbout, Integer> {
    UserAbout findByUserId(Integer userId);
}
