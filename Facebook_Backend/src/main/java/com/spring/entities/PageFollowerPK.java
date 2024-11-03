package com.spring.entities;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.io.Serializable;

@Embeddable
public class PageFollowerPK implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @NotNull
    @Column(name = "page_id")
    private Integer pageId;
    @NotNull
    @Column(name = "user_id")
    private Integer userId;
}
