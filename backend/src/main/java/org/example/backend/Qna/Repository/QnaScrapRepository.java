package org.example.backend.Qna.Repository;

import org.example.backend.Qna.model.Entity.QnaLike;
import org.example.backend.Qna.model.Entity.QnaScrap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface QnaScrapRepository extends JpaRepository<QnaScrap, Long> {
  // 어떤 유저가 게시글을 스크랩 했는지 확인하는 쿼리문 (후에 마이페이지에서 사용되기 때문에 scrap 전체를 추출)
    @Query("SELECT qs FROM QnaScrap qs WHERE qs.qnaBoard.id = :qnaBoardId AND qs.user.id = :userId")
    Optional<QnaScrap> findScrapByQnaBoardIdAndUserId(@Param("qnaBoardId") Long qnaBoardId, @Param("userId") Long userId);
}
