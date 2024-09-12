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
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QnaBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
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

    @Column(name = "answer_cnt", nullable = false, columnDefinition = "INT DEFAULT 0")
    private int answerCount;

    @Column(name = "enable", nullable = false)
    private boolean enable;

    @Column(name = "like_cnt", nullable = false, columnDefinition = "INT DEFAULT 0")
    private int likeCount;

    @Column(name = "hate_cnt", nullable = false, columnDefinition = "INT DEFAULT 0")
    private int hateCount;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;
}
