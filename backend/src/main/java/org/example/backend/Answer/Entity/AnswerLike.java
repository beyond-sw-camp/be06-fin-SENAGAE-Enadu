package org.example.backend.Answer.Entity;

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
public class AnswerLike {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id; // bigint

//        @ManyToOne(fetch = FetchType.LAZY)
//        @Column(name = "user_id", nullable = false)
//        private User user;

        @ManyToOne(fetch = FetchType.LAZY)
        @Column(name = "answer_id", nullable = false)
        private Answer anser;

        @Column(name = "state", nullable = false)
        private boolean state;

}
