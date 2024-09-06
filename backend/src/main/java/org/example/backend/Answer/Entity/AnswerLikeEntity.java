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
public class AnswerLikeEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id; // bigint

        @Column(name = "user_id", nullable = false)
        private Long userId; // bigint

        @Column(name = "answer_id", nullable = false)
        private Long answerId; // bigint

        @Column(name = "state", nullable = false)
        private boolean state; // boolean (추천/비추천)

}
