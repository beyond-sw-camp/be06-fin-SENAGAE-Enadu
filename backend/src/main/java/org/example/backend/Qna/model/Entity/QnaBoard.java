package org.example.backend.Qna.model.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.backend.Category.Model.Entity.Category;
import org.example.backend.ErrorArchive.Model.Entity.ErrorArchive;
import org.example.backend.User.Model.Entity.User;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Builder
public class QnaBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToOne(fetch = FetchType.LAZY)
    private ErrorArchive errorArchive;

    @OneToMany(mappedBy = "qnaBoard", fetch = FetchType.LAZY)
    private List<Answer> answerList;

    @OneToMany(mappedBy = "qnaBoard", fetch = FetchType.LAZY)
    private List<QnaLike> qnaLikeList;

    @OneToMany(mappedBy = "qnaBoard", fetch = FetchType.LAZY)
    private List<QnaScrap> qnaScrapList;


    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @Column(name = "content", columnDefinition = "text", nullable = false)
    private String content;

    @Builder.Default()
    @Column(name = "answer_cnt", nullable = false)
    private Integer answerCount = 0;

    @Builder.Default()
    @Column(name = "enable", nullable = false)
    private boolean enable = true;

    @Builder.Default()
    @Column(name = "like_cnt", nullable = false)
    private Integer likeCount = 0;

    @Builder.Default()
    @Column(name = "hate_cnt", nullable = false)
    private Integer hateCount = 0;


    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    public void increaseLikeCount() {
        this.likeCount++;
    }

    public void decreaseLikeCount() {
        this.likeCount--;
    }

    public void increaseHateCount() {
        this.hateCount++;
    }

    public void decreaseHateCount() {
        this.hateCount--;
    }

    public void increaseAnswerCount() {
        this.answerCount++;
    }

    public void decreaseAnswerCount() {
        this.answerCount--;
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void updateCategory(Category category) {
        this.category = category;
    }

    public void disable() {
        this.enable = false;
    }
}
