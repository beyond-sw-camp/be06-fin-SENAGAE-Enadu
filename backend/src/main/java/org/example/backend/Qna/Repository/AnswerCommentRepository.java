package org.example.backend.Qna.Repository;

import org.example.backend.Qna.model.Entity.AnswerComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerCommentRepository extends JpaRepository<AnswerComment, Long> {
}