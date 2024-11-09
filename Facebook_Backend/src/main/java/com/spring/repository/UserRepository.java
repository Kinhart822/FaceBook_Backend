package com.spring.repository;

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
               ab.user_name,
               p.image_url
        FROM users u
        JOIN user_about ab ON u.id = ab.user_id
        JOIN photo p ON u.id = p.user_id AND p.user_about_avatar_id = ab.id                    
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
            u.date_of_birth,
            p.image_url
        FROM users u
        JOIN user_about ab ON u.id = ab.user_id
        JOIN photo p ON u.id = p.user_id AND p.user_about_avatar_id = ab.id    
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
                u.date_of_birth,
                p.image_url
            FROM users u
            JOIN user_about ab ON u.id = ab.user_id
            JOIN photo p ON u.id = p.user_id AND p.user_about_avatar_id = ab.id    
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

    List<User> findAllBy();
}
