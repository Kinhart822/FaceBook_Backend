package com.spring.repository;

import com.spring.dto.Response.User.UserProjection;
import com.spring.dto.Response.User.UserResponse;
import com.spring.entities.PageFollower;
import com.spring.entities.PageFollowerPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    @Query(nativeQuery = true, value = """
            select u.id         as userId,
                   u.first_name as firstName,
                   u.last_name  as lastName,
                   u.avatar_url  as avatarUrl
            from page_follower pf
                     join users u on pf.user_id = u.id
            where pf.page_id = :pageId
              and pf.followed = 1""")
    List<UserProjection> getPageFollowers(@Param("pageId") Integer pageId);
    @Query(nativeQuery = true, value = """
            select u.id         as userId,
                   u.first_name as firstName,
                   u.last_name  as lastName,
                   u.avatar_url  as avatarUrl
            from page_follower pf
                     join users u on pf.user_id = u.id
            where pf.page_id = :pageId
              and pf.liked = 1""")
    List<UserProjection> getPageLikers(@Param("pageId") Integer pageId);
}
