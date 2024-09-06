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
public class AnswerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // bigint

    @Column(name = "qna_board_id", nullable = false)
    private Long qnaBoardId; // bigint

    @Column(name = "user_id", nullable = false)
    private Long userId; // bigint

    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content; // text

    @Column(name = "adopted", nullable = false)
    private boolean adopted; // boolean

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt; // datetime

    @Column(name = "enable", nullable = false)
    private boolean enable; // boolean

    @Column(name = "like_cnt", nullable = false)
    private int likeCnt; // int

    @Column(name = "hate_cnt", nullable = false)
    private int hateCnt; // int

}
