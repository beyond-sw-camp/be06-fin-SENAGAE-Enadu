package org.example.backend.Answer.Model.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.backend.Qna.model.Entity.QnaBorad;
import org.example.backend.User.Model.Entity.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qna_board_id", nullable = false)
    private QnaBorad qnaBorad;

    @OneToMany(mappedBy = "answer_commnet", fetch = FetchType.LAZY)
    List<AnswerComment> answerCommentList = new ArrayList<>();

    @OneToMany(mappedBy = "answer_like", fetch = FetchType.LAZY)
    List<AnswerLike> answerLikeList = new ArrayList<>();


    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "adopted", nullable = false)
    private boolean adopted;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "enable", nullable = false)
    private boolean enable;

    @Column(name = "like_cnt", nullable = false)
    private int likeCnt;

    @Column(name = "hate_cnt", nullable = false)
    private int hateCnt;

}
