package com.spring.repository;

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
}