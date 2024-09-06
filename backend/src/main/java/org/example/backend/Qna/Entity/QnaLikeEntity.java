package org.example.backend.Qna.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QnaLikeEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id; // bigint

        @Column(name = "user_id", nullable = false)
        private Long userId; // bigint

        @Column(name = "qna_board_id", nullable = false)
        private Long qnaBoardId; // bigint

        @Column(name = "state", nullable = false)
        private boolean state; // boolean (추천: true, 비추천: false)
}
