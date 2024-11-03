package com.spring.repository;

import com.spring.entities.PageFollower;
import com.spring.entities.PageFollowerPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PageFollowerRepository extends JpaRepository<PageFollower, PageFollowerPK> {
}
