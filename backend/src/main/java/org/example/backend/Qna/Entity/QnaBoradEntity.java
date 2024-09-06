package org.example.backend.Qna.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;


@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QnaBoradEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id; // bigint

        @Column(name = "user_id", nullable = false)
        private Long userId; // bigint

        @Column(name = "category_id", nullable = false)
        private Long categoryId; // bigint

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
