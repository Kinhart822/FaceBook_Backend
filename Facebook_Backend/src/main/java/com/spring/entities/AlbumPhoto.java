package com.spring.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "album_photo")
@Getter
@Setter
public class AlbumPhoto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    private AlbumPhotoPK albumPhotoPK;
}
