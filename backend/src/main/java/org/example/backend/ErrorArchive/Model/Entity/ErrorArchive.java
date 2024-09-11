package org.example.backend.ErrorArchive.Model.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.backend.Category.Model.Entity.Category;
import org.example.backend.User.Model.Entity.User;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ErrorArchive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // bigint

    @OneToMany(mappedBy = "errorArchive", fetch = FetchType.LAZY)
    private List<ErrorScrap> errorScrapList; // bigint

    @OneToMany(mappedBy = "errorArchive", fetch = FetchType.LAZY)
    private List<ErrorLike> errorLikeList; // bigint


    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category; // bigint

    @Column(name = "title", length = 100, nullable = false)
    private String title; // varchar(100)

    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content; // text

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt; // datetime

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt; // datetime (NULL 가능)

    @Column(name = "enable", nullable = false)
    @Builder.Default
    private boolean enable = true; // boolean

    @Column(name = "like_cnt", nullable = false)
    private int likeCount; // int

    @Column(name = "hate_cnt", nullable = false)
    private int hateCount; // int
    @PrePersist
    public void createdAt() {
        this.createdAt = Timestamp.from(Instant.now()).toLocalDateTime();
    }

    @PreUpdate
    public void modifiedAt() {
        this.modifiedAt = Timestamp.from(Instant.now()).toLocalDateTime();
    }



}
