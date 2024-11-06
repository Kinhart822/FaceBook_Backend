package com.spring.repository;

import com.spring.entities.GroupPost;
import com.spring.entities.Video;
import com.spring.entities.VideoPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface VideoRepository extends JpaRepository<Video, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Video v WHERE v.groupPost = :groupPost")
    void deleteByGroupPost(GroupPost groupPost);
}
