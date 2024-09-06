package org.example.backend.Qna.model.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.backend.Answer.Model.Entity.Answer;
import org.example.backend.Category.Model.Entity.Category;
import org.example.backend.ErrorArchive.Model.Entity.ErrorArchive;
import org.example.backend.User.Model.Entity.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QnaBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // bigint

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToOne(fetch = FetchType.LAZY)
    private ErrorArchive errorArchive;

    @OneToMany(mappedBy = "qnaBoard", fetch = FetchType.LAZY)
    private List<Answer> answerList;


    @Column(name = "title", length = 100, nullable = false)
    private String title; // varchar(100)

    @Column(name = "content", columnDefinition = "text", nullable = false)
    private String content; // text

    @Column(name = "answer_cnt", nullable = false)
    private int answerCount; // int

    @Column(name = "enable", nullable = false)
    private boolean enable; // boolean

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt; // datetime

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt; // datetime

    @Column(name = "like_cnt", nullable = false)
    private int likeCount; // int

    @Column(name = "hate_cnt", nullable = false)
    private int hateCount; // int
}
