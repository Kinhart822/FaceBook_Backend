package com.spring.repository;

import com.spring.entities.PageFollower;
import com.spring.entities.PageFollowerPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PageFollowerRepository extends JpaRepository<PageFollower, PageFollowerPK> {
    @Query(nativeQuery = true, value = """
            select count(*) as countFollowsByPageId
            from page_follower pf
            where pf.page_id = :pageId
                and pf.followed = 1""")
    Integer countFollowsByPageId(@Param("pageId") Integer pageId);
    @Query(nativeQuery = true, value = """
            select count(*) as countLikesByPageId
            from page_follower pf
            where pf.page_id = :pageId
                and pf.liked = 1""")
    Integer countLikesByPageId(@Param("pageId") Integer pageId);
}
