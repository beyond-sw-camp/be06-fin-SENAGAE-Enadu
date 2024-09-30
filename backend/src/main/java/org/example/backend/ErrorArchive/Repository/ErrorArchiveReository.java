package org.example.backend.ErrorArchive.Repository;

import org.example.backend.ErrorArchive.Model.Entity.ErrorArchive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.example.backend.ErrorArchive.Model.Entity.ErrorLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ErrorArchiveReository extends JpaRepository<ErrorArchive, Long> {
    // ErrorArchive 엔터티를 데이터베이스에서 categoryName
    // 어떤 엔터티를 쓸지, id의 타입
    @Query("SELECT el FROM ErrorLike el WHERE el.errorArchive.id = :errorArchiveId AND el.user.id = :userId AND el.state = :state")
    Optional<ErrorLike> findLikeByErrorArchiveIdAndUserIdAndState(
            @Param("errorArchiveId") Long errorArchiveId,
            @Param("userId") Long userId,
            @Param("state") boolean state);
    @Query("SELECT ea FROM ErrorArchive  ea JOIN FETCH ea.user JOIN FETCH ea.category WHERE ea.enable = true")
    Page<ErrorArchive> findByEnableTrue(Pageable pageable);


    @Query("SELECT er FROM ErrorArchive er JOIN FETCH er.user " +
            "WHERE er.category.id = :categoryId " +
            "AND er.enable = true")
    Page<ErrorArchive> findAllByCategoryId(Long categoryId, Pageable pageable);

    @Query("SELECT er FROM ErrorArchive er JOIN FETCH er.user JOIN FETCH er.category  JOIN FETCH er.category.superCategory " +
            "WHERE (LOWER(er.title) LIKE CONCAT('%', LOWER(:keyword), '%')) " +
            "AND er.enable = true")
    Page<ErrorArchive> findAllByTitleIsContainingIgnoreCase(String keyword, Pageable pageable);

    @Query("SELECT er FROM ErrorArchive er JOIN FETCH er.user JOIN FETCH er.category  JOIN FETCH er.category.superCategory " +
            "WHERE (LOWER(er.content) LIKE CONCAT('%', LOWER(:keyword), '%')) " +
            "AND er.enable = true")
    Page<ErrorArchive> findAllByContentIsContainingIgnoreCase(String keyword, Pageable pageable);

    @Query("SELECT er FROM ErrorArchive  er JOIN FETCH er.user JOIN FETCH er.category  JOIN FETCH er.category.superCategory " +
            "WHERE (LOWER(er.title) LIKE CONCAT('%', :keyword,'%') " +
            "OR LOWER(er.content) LIKE CONCAT('%', :keyword,'%')) AND er.enable = true")
    Page<ErrorArchive> findAllByKeyword(String keyword, Pageable pageable); // 메서드명 줄이기 위해 query 사용

    @Query("SELECT er FROM ErrorArchive er JOIN FETCH er.user " +
            "WHERE (LOWER(er.title) LIKE CONCAT('%', LOWER(:keyword), '%')) " +
            "AND er.category.id = :categoryId AND er.enable = true")
    Page<ErrorArchive> findAllByTitleIsContainingIgnoreCaseAndCategoryId(String keyword, Long categoryId, Pageable pageable);

    @Query("SELECT er FROM ErrorArchive er JOIN FETCH er.user " +
            "WHERE (LOWER(er.content) LIKE CONCAT('%', LOWER(:keyword), '%')) " +
            "AND er.category.id = :categoryId AND er.enable = true")
    Page<ErrorArchive> findAllByContentIsContainingIgnoreCaseAndCategoryId(String keyword, Long categoryId, Pageable pageable);

    @Query("SELECT er FROM ErrorArchive er JOIN FETCH er.user " +
            "WHERE (LOWER(er.title) LIKE CONCAT('%', :keyword,'%') " +
            "OR LOWER(er.content) LIKE CONCAT('%', :keyword,'%')) AND er.category.id = :categoryId AND er.enable")
    Page<ErrorArchive> findAllByKeywordAndCategory(String keyword, Long categoryId, Pageable pageable);

//    Page<ErrorArchive> findByUserIdAndEnableTrue(Long id, Pageable pageable);

//    Page<ErrorArchive> findByErrorScrapListUserIdAndEnableTrue(Long id, Pageable pageable);

    @Query("SELECT ea FROM ErrorArchive ea " +
            "JOIN FETCH ea.category c " +
            "LEFT JOIN FETCH c.superCategory sc " +
            "JOIN FETCH ea.user u " +
            "WHERE ea.user.id = :userId AND ea.enable = true")
    Page<ErrorArchive> findByUserIdAndEnableTrueWithFetch(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT DISTINCT e FROM ErrorArchive e " +
            "JOIN FETCH e.errorScrapList es " +
            "JOIN FETCH e.category c " +
            "LEFT JOIN FETCH c.superCategory sc " +
            "JOIN FETCH e.user u " +
            "WHERE es.user.id = :userId AND e.enable = true")
    Page<ErrorArchive> findByErrorScrapListUserIdAndEnableTrueWithFetch(@Param("userId") Long userId, Pageable pageable);

}
