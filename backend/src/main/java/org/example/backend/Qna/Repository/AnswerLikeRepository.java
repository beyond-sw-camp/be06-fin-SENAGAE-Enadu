package org.example.backend.Qna.Repository;

import org.example.backend.Qna.model.Entity.AnswerLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AnswerLikeRepository extends JpaRepository<AnswerLike, Long> {
    // 어떤 유저가 답변에 좋아요 또는 싫어요 혹은 미표기 했는지 확인하는 쿼리문
    @Query("SELECT al.state FROM AnswerLike al WHERE al.answer.id = :answerId AND al.user.id = :userId AND al.answer.enable = TRUE")
    Optional<Boolean> findState(@Param("answerId") Long answerId, @Param("userId") Long userId);

    Optional<AnswerLike> findByAnswerIdAndUserIdAndStateAndAnswerEnableTrue(Long answerId, Long userId, boolean state);


}
