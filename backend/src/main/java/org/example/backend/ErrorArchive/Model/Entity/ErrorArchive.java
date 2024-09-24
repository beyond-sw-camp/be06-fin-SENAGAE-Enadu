package org.example.backend.ErrorArchive.Model.Entity;

import jakarta.persistence.*;
import lombok.*;
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


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

    private int scrapCount; // 스크랩 수를 저장할 필드


    public void setLikeCnt(int likeCount) {
        this.likeCount = likeCount;
    }
    public void setHateCnt(int hateCount) {
        this.hateCount = hateCount;
    }
    public void setScrapCount(int scrapCount) {
        this.scrapCount = scrapCount;
    }

    public void updateTitle(String title){
        this.title = title;
    }
    public void updateContent(String content){
        this.content = content;
    }
    public void updateCategory(Category category){
        this.category = category;
    }
}

