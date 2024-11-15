package com.spring.entities;

import com.spring.enums.ActionPerformed;
import com.spring.enums.PostStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "group_post")
public class GroupPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    private Group group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "message", length = 2000, nullable = false)
    private String message;

    @Column(name = "date_created", updatable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateCreated;

    @Column(name = "date_updated")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateUpdated;

    @Column(name = "action_performed")
    @Enumerated(EnumType.ORDINAL)
    private ActionPerformed actionPerformed;

    @Column(name = "post_status")
    @Enumerated(EnumType.ORDINAL)
    private PostStatus postStatus;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "groupPost", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "groupPost", cascade = CascadeType.ALL)
    private List<Notification> notifications;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "groupPost", cascade = CascadeType.ALL)
    private List<UserLikePost> userLikePosts;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "groupPost", cascade = CascadeType.ALL)
    private List<Photo> photos;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "groupPost", cascade = CascadeType.ALL)
    private List<Video> videoList;
}

