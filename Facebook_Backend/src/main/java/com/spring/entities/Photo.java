package com.spring.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "photo")
@Getter
@Setter
public class Photo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "image_url")
    private String imageUrl;
    @CreatedDate
    @Column(name = "upload_date")
    @JsonIgnore
    private Instant uploadDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_post_id", referencedColumnName = "id")
    private UserPost userPost;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_about_background_id", referencedColumnName = "id")
    private UserAbout background;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_about_avatar_id", referencedColumnName = "id")
    private UserAbout avatar;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_about_profile_photo_id", referencedColumnName = "id")
    private UserAbout profilePhoto;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "background_group_id", referencedColumnName = "id")
    private Group backgroundGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_post_id", referencedColumnName = "id")
    private GroupPost groupPost;
}
