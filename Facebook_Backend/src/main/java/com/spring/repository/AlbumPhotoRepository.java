package com.spring.repository;

import com.spring.entities.AlbumPhoto;
import com.spring.entities.AlbumPhotoPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumPhotoRepository extends JpaRepository<AlbumPhoto, AlbumPhotoPK> {
}
