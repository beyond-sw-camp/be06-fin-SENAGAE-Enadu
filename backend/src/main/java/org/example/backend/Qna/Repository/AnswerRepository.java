package org.example.backend.Qna.Repository;

import org.example.backend.Qna.model.Entity.Answer;
import org.example.backend.Qna.model.Entity.QnaBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    @Query("SELECT a FROM Answer a WHERE a.id = :id AND a.enable=true")
    Optional<Answer> findById(Long id);

    @Query("SELECT a FROM Answer a WHERE a.enable=true")
    Page<Answer> findAll(Pageable pageable);

    //채택된 답변의 수를 확인하는 쿼리문
    @Query("SELECT COUNT(a) FROM Answer a JOIN a.qnaBoard q WHERE q.id = :qnaBoardId AND a.adopted = TRUE AND a.enable = TRUE AND q.enable = TRUE")
    Integer countAdopted(Long qnaBoardId);

}