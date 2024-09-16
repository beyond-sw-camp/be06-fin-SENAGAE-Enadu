package org.example.backend.ErrorArchive.Model.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.backend.Category.Model.Entity.Category;
import org.example.backend.User.Model.Entity.User;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@EntityListeners(AuditingEntityListener.class)
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
    @CreatedDate
    private LocalDateTime createdAt; // datetime

    @Column(name = "modified_at")
    @LastModifiedDate
    private LocalDateTime modifiedAt; // datetime (NULL 가능)

    @Column(name = "enable", nullable = false)
    @Builder.Default
    private boolean enable = true; // boolean

    @Column(name = "like_cnt", nullable = false)
    private int likeCount; // int

    @Column(name = "hate_cnt", nullable = false)
    private int hateCount; // int




}
