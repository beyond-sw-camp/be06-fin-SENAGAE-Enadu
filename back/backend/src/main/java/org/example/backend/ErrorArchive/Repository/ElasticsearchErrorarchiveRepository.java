package org.example.backend.ErrorArchive.Repository;


import org.example.backend.ErrorArchive.Model.Doc.ErrorArchive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


public interface ElasticsearchErrorarchiveRepository extends ElasticsearchRepository<ErrorArchive, Long> {

//    // 제목 또는 내용에서 검색 (검색어 포함)
//    Page<ErrorArchive> findByTitleContainingOrContentContainingAndEnableTrue(String title, String content, Pageable pageable);
//
//    // 제목에서 검색 (검색어 포함)
//    Page<ErrorArchive> findByTitleContainingAndEnableTrue(String title, Pageable pageable);
//
//    // 내용에서 검색 (검색어 포함)
//    Page<ErrorArchive> findByContentContainingAndEnableTrue(String content, Pageable pageable);
//
//    // 제목 또는 내용에서 카테고리 ID로 검색 (검색어 포함)
//    Page<ErrorArchive> findByTitleContainingOrContentContainingAndCategoryIdAndEnableTrue(String title, String content, Long categoryId, Pageable pageable);
//
//    // 제목에서 카테고리 ID로 검색 (검색어 포함)
//    Page<ErrorArchive> findByTitleContainingAndCategoryIdAndEnableTrue(String title, Long categoryId, Pageable pageable);
//
//    // 내용에서 카테고리 ID로 검색 (검색어 포함)
//    Page<ErrorArchive> findByContentContainingAndCategoryIdAndEnableTrue(String content, Long categoryId, Pageable pageable);
//
//    // 특정 카테고리 ID로만 검색
//    Page<ErrorArchive> findByCategoryIdAndEnableTrue(Long categoryId, Pageable pageable);
//    // QueryBuilder를 사용한 검색 메서드




}





