package com.spring.repository;

import com.spring.entities.AlbumPhoto;
import com.spring.entities.AlbumPhotoPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumPhotoRepository extends JpaRepository<AlbumPhoto, AlbumPhotoPK> {
    @Query(nativeQuery = true, value = """
            select count(*) as countByAlbumId
            from album_photo ap
            where ap.album_id = :albumId""")
    Integer countByAlbumId(@Param("albumId") Integer albumId);
}
