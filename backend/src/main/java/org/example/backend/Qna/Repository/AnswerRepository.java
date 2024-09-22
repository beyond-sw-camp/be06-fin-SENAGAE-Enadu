package org.example.backend.Qna.Repository;

import org.example.backend.Qna.model.Entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

}