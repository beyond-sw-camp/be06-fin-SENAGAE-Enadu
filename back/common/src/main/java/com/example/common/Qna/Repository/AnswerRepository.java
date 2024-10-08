package com.example.common.Qna.Repository;

import com.example.common.Qna.model.Entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Optional<Answer> findByIdAndEnableTrue(Long id);

    //채택된 답변의 수를 확인하는 쿼리문
    @Query("SELECT COUNT(a) FROM Answer a JOIN a.qnaBoard q WHERE q.id = :qnaBoardId AND a.adopted = TRUE AND a.enable = TRUE AND q.enable = TRUE")
    Integer countAdopted(Long qnaBoardId);

}