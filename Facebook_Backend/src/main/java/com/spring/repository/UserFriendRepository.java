package com.spring.repository;

import com.spring.dto.response.UserProjectionNew;
import com.spring.entities.UserFriend;
import com.spring.enums.FriendRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserFriendRepository extends JpaRepository<UserFriend, Integer> {
    Optional<UserFriend> findBySourceUserIdAndTargetUserId(Integer sourceId, Integer targetId);

    @Query("SELECT uf FROM UserFriend uf WHERE uf.sourceUser.id = :sourceId AND uf.targetUser.id = :targetId AND uf.status = :status")
    Optional<UserFriend> findBySourceUserIdAndTargetUserIdAndStatus(@Param("sourceId") Integer sourceId, @Param("targetId") Integer targetId, @Param("status") FriendRequestStatus status);

    @Query("SELECT uf FROM UserFriend uf WHERE uf.targetUser.id = :targetId AND uf.status = :status")
    List<UserFriend> findByTargetUserIdAndFriendRequestStatus(@Param("targetId") Integer targetId, @Param("status") FriendRequestStatus status);

    @Query(nativeQuery = true, value = """
        select u.id                                   as userId,
               u.avatar_b64                           as avatarB64,
               concat(u.first_name, ' ', u.last_name) as fullname
        from users u
                 left join user_friend uf ON u.id = uf.source_id
        where u.id != :id
          and not exists(select 1
                         from user_friend uf
                         where uf.source_id = :id
                           and uf.target_id = u.id)
          and concat(u.first_name, ' ', u.last_name) like concat('%', :name, '%');""")
    List<UserProjectionNew> findStrangers(@Param("id") Integer userId, @Param("name") String name);
}
