package com.spring.repository;

import com.spring.entities.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Integer> {
    @Query(nativeQuery = true, value = """
        SELECT g.title, 
               g.description, 
               p.image_url AS background
        FROM groups g
        LEFT JOIN photo p ON p.group_id = g.id
        WHERE g.title LIKE CONCAT('%', :title, '%')
        LIMIT :limit OFFSET :offset
        """)
    List<Object[]> getAllGroupsByTitle(
            @Param("title") String title,
            @Param("limit") Integer limit,
            @Param("offset") Integer offset
    );
}

