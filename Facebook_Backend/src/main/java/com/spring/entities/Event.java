package com.spring.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "event")
@Getter
@Setter
public class Event implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "type_id")
    private Integer typeId;
    @Column(name = "photo_id")
    private Integer photoId;
    @Column(name = "video_id")
    private Integer videoId;
    @Column(name = "start_date")
    private Instant startDate;
    @Column(name = "end_time")
    private Instant endDate;
    @CreatedBy
    @Column(name = "created_by")
    @JsonIgnore
    private Integer createdBy;
    @LastModifiedBy
    @Column(name = "updated_by")
    @JsonIgnore
    private Integer updatedBy;
}
