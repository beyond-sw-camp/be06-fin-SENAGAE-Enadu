package org.example.backend.Qna.Repository;

import org.example.backend.Qna.model.Entity.QnaBoard;
import org.example.backend.Qna.model.Entity.QnaLike;
import org.example.backend.Qna.model.Entity.QnaScrap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface QnaLikeRepository extends JpaRepository<QnaLike, Long> {
//    // 게시판의 좋아요 또는 싫어요 수를 세는 쿼리
//    @Query("SELECT COUNT(l) FROM QnaLike l WHERE l.qnaBoard = :qnaBoard AND l.state = :state")
//    Integer QnaLikeCountByQnaBoard(@Param("qnaBoard") QnaBoard qnaBoard, @Param("state") boolean state);

    // 어떤 유저가 게시글에 좋아요 또는 싫어요 혹은 미표기 했는지 확인하는 쿼리문
    @Query("SELECT ql.state FROM QnaLike ql WHERE ql.qnaBoard.id = :qnaBoardId AND ql.user.id = :userId")
    Optional<Boolean> findStateByQnaBoardIdAndUserId(@Param("qnaBoardId") Long qnaBoardId, @Param("userId") Long userId);

    @Query("SELECT ql FROM QnaLike ql WHERE ql.qnaBoard.id = :qnaBoardId AND ql.user.id = :userId AND ql.state = :state")
    Optional<QnaLike> findLikeByQnaBoardIdAndUserIdAndState(@Param("qnaBoardId") Long qnaBoardId, @Param("userId") Long userId, @Param("state") Boolean state);
}
