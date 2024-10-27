package com.spring.entities;

import com.spring.enums.EducationLevel;
import com.spring.enums.Relationship;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_about")
public class UserAbout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "user_name", length = 50)
    private String userName;

    @Column(name = "occupation", length = 100)
    private String occupation;

    @Column(name = "work_place", length = 100)
    private String workPlace;

    @Column(name = "education_level")
    @Enumerated(EnumType.ORDINAL)
    private EducationLevel educationLevel;

    @Column(name = "school", length = 100)
    private String school;

    @Column(name = "date_of_joining")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfJoining;

    @OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinColumn(name = "location_id")
    private Location location;

    @Column(name = "relation_ship", length = 50)
    private Relationship relationship;
}

