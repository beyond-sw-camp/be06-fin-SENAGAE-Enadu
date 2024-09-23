package org.example.backend.Qna.Repository;

import org.example.backend.Qna.model.Entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Optional<Answer> findAllByQnaBoardIdAndId(Long Id, Long qnaBoardId);

    //채택된 답변의 수를 확인하는 쿼리문
    @Query("SELECT COUNT(a) FROM Answer a JOIN a.qnaBoard q WHERE q.id = :qnaBoardId AND a.adopted = TRUE")
    Integer countAdoptedAnswersByQuestionId(@Param("qnaBoardId") Long qnaBoardId);
}