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
public class QnaLike {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

//        @ManyToOne(fetch = FetchType.LAZY)
//        @Column(name = "user_id", nullable = false)
//        private User user;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "qna_board_id", nullable = false)
        private QnaBorad qnaBoard;

        @Column(name = "state", nullable = false)
        private boolean state;
}
