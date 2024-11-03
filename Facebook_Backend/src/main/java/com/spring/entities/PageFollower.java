package com.spring.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "page_follower")
public class PageFollower implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    private PageFollowerPK pageFollowerPK;
    @Column(name = "liked")
    private boolean liked;
    @Column(name = "followed")
    private boolean followed;
}
