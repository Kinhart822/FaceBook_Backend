package com.spring.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "photo_tag")
@Getter
@Setter
public class PhotoTag implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    private PhotoTagPK photoTagPK;
}
