package com.spring.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "groups")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    private User createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by", referencedColumnName = "id")
    private User updatedBy;

    @Column(name = "title", length = 100)
    private String title;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "date_created", updatable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateCreated;

    @Column(name = "date_updated")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateUpdated;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "group")
    private List<GroupMember> groupMemberList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "group")
    private List<GroupMessage> groupMessageList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "group")
    private List<GroupPost> groupPostList;
}
