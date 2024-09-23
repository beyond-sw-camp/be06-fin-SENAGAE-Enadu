package org.example.backend.Qna.model.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.backend.User.Model.Entity.User;
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
    private boolean adopted=false;

    @Builder.Default()
    @Column(name = "enable", nullable = false)
    private boolean enable=true;

    @Builder.Default()
    @Column(name = "like_cnt", nullable = false)
    private Integer likeCount=0;

    @Builder.Default()
    @Column(name = "hate_cnt", nullable = false)
    private Integer hateCount=0;

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

    public void adoptedAnswer(boolean adopted) {
        this.adopted = adopted;
    }

}
