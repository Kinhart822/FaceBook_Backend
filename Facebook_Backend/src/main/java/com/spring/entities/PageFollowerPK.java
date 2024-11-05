package com.spring.entities;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
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
