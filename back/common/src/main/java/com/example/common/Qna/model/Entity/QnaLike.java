package com.example.common.Qna.model.Entity;

import jakarta.persistence.*;
import lombok.*;
import com.example.common.User.Model.Entity.User;


@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QnaLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qna_board_id", nullable = false)
    private QnaBoard qnaBoard;

    @Column(name = "state", nullable = false)
    private boolean state;

    public void changeState() {
        this.state = !this.state;
    }
}
