package org.example.backend.Qna.Repository;

import org.example.backend.Qna.model.Entity.QnaLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface QnaLikeRepository extends JpaRepository<QnaLike, Long> {
    // 어떤 유저가 게시글에 좋아요 또는 싫어요 혹은 미표기 했는지 확인하는 쿼리문
    @Query("SELECT ql.state FROM QnaLike ql WHERE ql.qnaBoard.id = :qnaBoardId AND ql.user.id = :userId AND ql.qnaBoard.enable = true")
    Optional<Boolean> findState(@Param("qnaBoardId") Long qnaBoardId, @Param("userId") Long userId);

    Optional<QnaLike> findByUserIdAndQnaBoardIdAndState(Long id, Long id1, boolean state);

}
