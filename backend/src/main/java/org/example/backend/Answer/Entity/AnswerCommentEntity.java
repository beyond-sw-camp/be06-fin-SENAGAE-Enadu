package org.example.backend.Answer.Entity;

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
public class AnswerCommentEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id; // bigint

        @Column(name = "answer_id", nullable = false)
        private Long answerId; // bigint

        @Column(name = "user_id", nullable = false)
        private Long userId; // bigint

        @Column(name = "super_comment_id")
        private Long superCommentId; // bigint (NULL 가능)

        @Column(name = "content", columnDefinition = "TEXT", nullable = false)
        private String content; // text

        @Column(name = "created_at", nullable = false)
        private LocalDateTime createdAt; // datetime

        @Column(name = "enable", nullable = false)
        private boolean enable; // boolean

}
