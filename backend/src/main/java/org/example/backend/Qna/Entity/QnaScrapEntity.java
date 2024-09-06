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
public class QnaScrapEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id; // bigint

        @Column(name = "user_id", nullable = false)
        private Long userId; // bigint

        @Column(name = "qna_board_id", nullable = false)
        private Long qnaBoardId; // bigint
}
