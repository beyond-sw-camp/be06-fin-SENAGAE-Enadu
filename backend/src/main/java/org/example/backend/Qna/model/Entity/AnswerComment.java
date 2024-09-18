package org.example.backend.Qna.model.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.backend.User.Model.Entity.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnswerComment {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "answer_id", nullable = false)
        private Answer answer;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id", nullable = false)
        private User user;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "super_comment_id")
        private AnswerComment answerComment;

        @OneToMany(mappedBy = "answerComment", cascade = CascadeType.ALL)
        private List<AnswerComment> subCommentList = new ArrayList<>();

        @Column(name = "content", columnDefinition = "TEXT", nullable = false)
        private String content;

        @Column(name = "created_at", nullable = false)
        private LocalDateTime createdAt;

        @Column(name = "enable", nullable = false)
        private boolean enable;

}
