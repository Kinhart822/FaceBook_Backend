package com.spring.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "album")
@Getter
@Setter
public class Album implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @CreatedDate
    @Column(name = "date_created")
    @JsonIgnore
    private Instant dateCreated;
    @LastModifiedDate
    @Column(name = "date_updated")
    @JsonIgnore
    private Instant dateUpdated;
}
