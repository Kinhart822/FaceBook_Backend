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
    @Column(name = "caption")
    private String caption;
    @Column(name = "image_url")
    private String imageUrl;
    @CreatedDate
    @Column(name = "upload_date")
    @JsonIgnore
    private Instant uploadDate;

}
