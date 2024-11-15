package com.spring.entities;

import com.spring.enums.FollowerStatus;
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
@Table(name = "user_follower")
public class UserFollower {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_id", referencedColumnName = "id")
    private User sourceUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_id", referencedColumnName = "id")
    private User targetUser;

    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    private FollowerStatus status;

    @Column(name = "date_created", updatable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateCreated;

    @Column(name = "date_updated")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateUpdated;
}
