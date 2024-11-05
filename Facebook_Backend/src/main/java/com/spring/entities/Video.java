package com.spring.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "video")
@Getter
@Setter
public class Video implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "length")
    private Integer length;
    @Column(name = "video_url")
    private String videoUrl;
    @CreatedBy
    @Column(name = "created_by")
    @JsonIgnore
    private Integer createdBy;
    @LastModifiedBy
    @Column(name = "updated_by")
    @JsonIgnore
    private Integer updatedBy;
    @CreatedDate
    @Column(name = "date_created")
    @JsonIgnore
    private Instant dateCreated;
    @LastModifiedDate
    @Column(name = "date_updated")
    @JsonIgnore
    private Instant dateUpdated;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_post_id", referencedColumnName = "id")
    private UserPost userPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_post_id", referencedColumnName = "id")
    private GroupPost groupPost;
}
