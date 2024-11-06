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
@Table(name = "video_post")
public class VideoPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "action_performed")
    @Enumerated(EnumType.ORDINAL)
    private ActionPerformed actionPerformed;

    @Column(name = "post_status")
    @Enumerated(EnumType.ORDINAL)
    private PostStatus postStatus;

    @OneToOne(mappedBy = "videoPost")
    private Video video;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "videoPost", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "videoPost", cascade = CascadeType.ALL)
    private List<Notification> notifications;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "videoPost", cascade = CascadeType.ALL)
    private List<UserLikePost> userLikePosts;
}

