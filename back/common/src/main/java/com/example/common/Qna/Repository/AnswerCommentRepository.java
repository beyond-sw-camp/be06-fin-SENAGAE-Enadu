package com.example.common.Qna.Repository;

import com.example.common.Qna.model.Entity.AnswerComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerCommentRepository extends JpaRepository<AnswerComment, Long> {
}