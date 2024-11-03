package com.spring.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.io.Serializable;

@Embeddable
public class PhotoTagPK implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @NotNull
    @Column(name = "photo_id")
    private Integer photoId;
    @NotNull
    @Column(name = "tagged_user_id")
    private Integer taggedUserId;
}
