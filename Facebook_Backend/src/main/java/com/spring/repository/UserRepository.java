package com.spring.repository;

import com.spring.dto.response.User.UserProjection;
import com.spring.dto.response.UserProjectionNew;
import com.spring.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.email = :email")
    List<User> findUserByEmail(@Param("email") String email);

    @Query(nativeQuery = true, value = """
        SELECT u.first_name,
            u.last_name,
            u.email,
            u.phone,
            u.date_of_birth,
            ab.user_name
        FROM users u
        JOIN user_about ab ON u.id = ab.user_id
        WHERE ab.user_name LIKE CONCAT('%', :userName, '%')
        AND u.id != :currentUserId  
        LIMIT :limit OFFSET :offset
        """)
    List<Object[]> getAllUsersByUserName(
            @Param("userName") String userName,
            @Param("currentUserId") Integer currentUserId,
            @Param("limit") Integer limit,
            @Param("offset") Integer offset
    );

    @Query(nativeQuery = true, value = """
        SELECT u.first_name,
            u.last_name,
            u.email,
            u.phone,
            u.date_of_birth
        FROM users u
        WHERE u.first_name LIKE CONCAT('%', :first_name, '%')
        AND u.id != :currentUserId  
        LIMIT :limit OFFSET :offset
        """)
    List<Object[]> getAllUsersByFirstName(
            @Param("first_name") String firstName,
            @Param("currentUserId") Integer currentUserId,
            @Param("limit") Integer limit,
            @Param("offset") Integer offset
    );

    @Query(nativeQuery = true, value = """
        SELECT u.first_name,
            u.last_name,
            u.email,
            u.phone,
            u.date_of_birth
        FROM users u
        WHERE u.last_name LIKE CONCAT('%', :last_name, '%')
        AND u.id != :currentUserId  
        LIMIT :limit OFFSET :offset
        """)
    List<Object[]> getAllUsersByLastName(
            @Param("last_name") String lastName,
            @Param("currentUserId") Integer currentUserId,
            @Param("limit") Integer limit,
            @Param("offset") Integer offset
    );

    @Query(nativeQuery = true, value = """
            select u.id                              as userId,
                   u.avatar_b64                      as avatarB64,
                   concat(u.first_name, ' ', u.last_name) as fullname
            from users u
            where u.id = :id;""")
    UserProjectionNew getProfile(@Param("id") Integer userId);
}
