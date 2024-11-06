package com.spring.repository;

import com.spring.entities.GroupPost;
import com.spring.entities.Photo;
import com.spring.entities.UserPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Photo p WHERE p.userPost = :userPost")
    void deleteByUserPost(UserPost userPost);

    @Transactional
    @Modifying
    @Query("DELETE FROM Photo p WHERE p.groupPost = :groupPost")
    void deleteByGroupPost(GroupPost groupPost);
}
