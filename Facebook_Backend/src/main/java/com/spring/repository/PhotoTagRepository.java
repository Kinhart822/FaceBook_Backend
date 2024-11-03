package com.spring.repository;

import com.spring.entities.PhotoTag;
import com.spring.entities.PhotoTagPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoTagRepository extends JpaRepository<PhotoTag, PhotoTagPK> {
}
