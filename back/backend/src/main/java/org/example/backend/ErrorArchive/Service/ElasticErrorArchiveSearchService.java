//package org.example.backend.ErrorArchive.Service;
//
//import com.example.common.Category.Model.Entity.Category;
//import com.example.common.Category.Repository.CategoryRepository;
//import com.example.common.User.Model.Entity.User;
//import lombok.RequiredArgsConstructor;
//import org.example.backend.Common.BaseResponseStatus;
//import org.example.backend.ErrorArchive.Model.Doc.ErrorArchive;
//import org.example.backend.ErrorArchive.Model.Req.GetErrorArchiveSearchReq;
//import org.example.backend.ErrorArchive.Model.Res.ListErrorArchiveRes;
//import org.example.backend.ErrorArchive.Repository.ElasticsearchErrorarchiveRepository;
//import org.example.backend.Exception.custom.InvalidCategoryException;
//import org.example.backend.Exception.custom.InvalidErrorBoardException;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
//@Service
//@RequiredArgsConstructor
//@Primary
//public class ElasticErrorArchiveSearchService implements ErrorArchiveSearchService{
//    private final ElasticsearchErrorarchiveRepository elasticsearchErrorarchiveRepository;
//    private final CategoryRepository categoryRepository;
//    private final static List<String> SEARCH_TYPE = List.of("tc", "t", "c");
//
//    public List<ListErrorArchiveRes> errorArchiveSearch(GetErrorArchiveSearchReq errorArchiveSearchReq) {
//        ValidateSearchReq(errorArchiveSearchReq);
//        Pageable pageable = getPageable(errorArchiveSearchReq);
//
//        Page<ErrorArchive> errorArchivePage;
//        Long categoryId = errorArchiveSearchReq.getCategoryId();
//        String keyword = errorArchiveSearchReq.getKeyword().strip().toLowerCase();
//
//        if (categoryId == null || categoryId == 0) { // 검색어로만 검색
//            errorArchivePage = searchByKeyword(errorArchiveSearchReq.getType(), keyword, pageable);
//        } else if (keyword.isEmpty()) { // 카테고리 ID로만 검색
//            errorArchivePage = elasticsearchErrorarchiveRepository.findByCategoryIdAndEnableTrue(categoryId, pageable);
//        } else { // 카테고리와 검색어로 검색
//            errorArchivePage = searchByKeywordAndCategory(errorArchiveSearchReq.getType(), keyword, categoryId, pageable);
//        }
//
//        return getListErrorArchiveRes(errorArchivePage);
//    }
//
//    private Pageable getPageable(GetErrorArchiveSearchReq errorArchiveSearchReq) {
//        if (errorArchiveSearchReq.getSort().equals("latest")) {
//            return PageRequest.of(errorArchiveSearchReq.getPage(), errorArchiveSearchReq.getSize(), Sort.by(Sort.Direction.DESC, "createdAt"));
//        } else {
//            return PageRequest.of(errorArchiveSearchReq.getPage(), errorArchiveSearchReq.getSize(),
//                    Sort.by(Sort.Direction.DESC, "likeCount").and(Sort.by(Sort.Direction.DESC, "createdAt")));
//        }
//    }
//
//    private Page<ErrorArchive> searchByKeyword(String type, String keyword, Pageable pageable) {
//        if (type.equals("tc")) {
//            return elasticsearchErrorarchiveRepository.findByTitleContainingOrContentContainingAndEnableTrue(keyword, keyword, pageable);
//        } else if (type.equals("t")) {
//            return elasticsearchErrorarchiveRepository.findByTitleContainingAndEnableTrue(keyword, pageable);
//        }
//        return elasticsearchErrorarchiveRepository.findByContentContainingAndEnableTrue(keyword, pageable);
//    }
//
//    private Page<ErrorArchive> searchByKeywordAndCategory(String type, String keyword, Long categoryId, Pageable pageable) {
//        if (type.equals("tc")) {
//            return elasticsearchErrorarchiveRepository.findByTitleContainingOrContentContainingAndCategoryIdAndEnableTrue(keyword, keyword, categoryId, pageable);
//        } else if (type.equals("t")) {
//            return elasticsearchErrorarchiveRepository.findByTitleContainingAndCategoryIdAndEnableTrue(keyword, categoryId, pageable);
//        }
//        return elasticsearchErrorarchiveRepository.findByContentContainingAndCategoryIdAndEnableTrue(keyword, categoryId, pageable);
//    }
//
//    private static List<ListErrorArchiveRes> getListErrorArchiveRes(Page<ErrorArchive> errorArchivePage) {
//        List<ListErrorArchiveRes> errorArchiveResList = new ArrayList<>();
//        for (ErrorArchive errorArchive : errorArchivePage) {
//            User user = errorArchive.getUser();
//            Category category = errorArchive.getCategory();
//            errorArchiveResList.add(ListErrorArchiveRes.builder()
//                    .id(errorArchive.getId())
//                    .title(errorArchive.getTitle())
//                    .content(errorArchive.getContent())
//                    .likeCnt(errorArchive.getLikeCnt())
//                    .createdAt(errorArchive.getCreatedAt().toString())
//                    .nickname(user.getNickname())
//                    .grade(user.getGrade())
//                    .profileImg(user.getProfileImg())
//                    .superCategory(category.getSuperCategory() != null ? category.getSuperCategory().getCategoryName() : category.getCategoryName())
//                    .subCategory(category.getSuperCategory() != null ? category.getCategoryName() : null)
//                    .build());
//        }
//        return errorArchiveResList;
//    }
//
//    private void ValidateSearchReq(GetErrorArchiveSearchReq errorArchiveSearchReq) {
//        String keyword = errorArchiveSearchReq.getKeyword().strip().toLowerCase();
//        if ((keyword.isEmpty()) && (errorArchiveSearchReq.getCategoryId() == null || errorArchiveSearchReq.getCategoryId() == 0)) {
//            throw new InvalidErrorBoardException(BaseResponseStatus.ERRORARCHIVE_SEARCH_EMPTY_REQUEST);
//        }
//        if (!SEARCH_TYPE.contains(errorArchiveSearchReq.getType())) {
//            throw new InvalidErrorBoardException(BaseResponseStatus.ERRORARCHIVE_INVALID_SEARCH_TYPE);
//        }
//        if (errorArchiveSearchReq.getCategoryId() != null && errorArchiveSearchReq.getCategoryId() != 0) {
//            categoryRepository.findById(errorArchiveSearchReq.getCategoryId()).orElseThrow(() -> new InvalidCategoryException(BaseResponseStatus.CATEGORY_NOT_FOUND_CATEGORY));
//        }
//    }
//
//}
//
