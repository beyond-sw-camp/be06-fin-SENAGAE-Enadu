package com.example.common.Qna.model.Entity;

import jakarta.persistence.*;
import lombok.*;
import com.example.common.User.Model.Entity.User;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Builder
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qna_board_id", nullable = false)
    private QnaBoard qnaBoard;

    @OneToMany(mappedBy = "answer", fetch = FetchType.LAZY)
    private List<AnswerComment> answerCommentList;

    @OneToMany(mappedBy = "answer", fetch = FetchType.LAZY)
    private List<AnswerLike> answerLikeList;


    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content;

    @Builder.Default()
    @Column(name = "adopted", nullable = false)
    private boolean adopted = false;

    @Builder.Default()
    @Column(name = "enable", nullable = false)
    private boolean enable = true;

    @Builder.Default()
    @Column(name = "comment_cnt", nullable = false)
    private Integer commentCount = 0;

    @Builder.Default()
    @Column(name = "like_cnt", nullable = false)
    private Integer likeCount = 0;

    @Builder.Default()
    @Column(name = "hate_cnt", nullable = false)
    private Integer hateCount = 0;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

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

    public void increaseCommentCount() {
        this.commentCount++;
    }

    public void decreaseCommentCount() {
        this.commentCount--;
    }

    public void adoptedAnswer(boolean adopted) {
        this.adopted = adopted;
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void disable() {
        this.enable = false;
    }

}
