package org.example.backend.ErrorArchive.Service;

import lombok.RequiredArgsConstructor;
import org.example.backend.Category.Model.Entity.Category;
import org.example.backend.Category.Repository.CategoryRepository;
import org.example.backend.Common.BaseResponseStatus;
import org.example.backend.ErrorArchive.Model.Entity.ErrorArchive;
import org.example.backend.ErrorArchive.Model.Req.GetErrorArchiveSearchReq;
import org.example.backend.ErrorArchive.Model.Res.ListErrorArchiveRes;
import org.example.backend.ErrorArchive.Repository.ErrorArchiveReository;
import org.example.backend.Exception.custom.InvalidCategoryException;
import org.example.backend.Exception.custom.InvalidErrorBoardException;
import org.example.backend.User.Model.Entity.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@Qualifier("DbSearch")
@RequiredArgsConstructor
public class DbErrorArchiveSearchService implements ErrorArchiveSearchService {
    private final ErrorArchiveReository errorArchiveReository;
    private final CategoryRepository categoryRepository;
    private final static List<String> SEARCH_TYPE = List.of("tc", "t", "c");

    @Override
    public List<ListErrorArchiveRes> errorArchiveSearch(GetErrorArchiveSearchReq errorArchiveSearchReq) {
        ValidateSearchReq(errorArchiveSearchReq);

        Pageable pageable;
        if (errorArchiveSearchReq.getSort().equals("latest")) {
            pageable = PageRequest.of(errorArchiveSearchReq.getPage(), errorArchiveSearchReq.getSize(),Sort.by(Sort.Direction.DESC, "createdAt"));
        } else {
            pageable = PageRequest.of(errorArchiveSearchReq.getPage(), errorArchiveSearchReq.getSize(),
                    Sort.by(Sort.Direction.DESC, "likeCount").and(Sort.by(Sort.Direction.DESC, "createdAt")));
        }

        Page<ErrorArchive> errorArchivePage;
        Long categoryId = errorArchiveSearchReq.getCategoryId();
        String keyword = errorArchiveSearchReq.getKeyword().strip().toLowerCase(); // 소문자로 변환, 문자열의 공백 삭제, keyword는 적어도 ''이므로 메서드에서 오류가 안남
        if (categoryId == null || categoryId == 0) { // 검색어로만 검색
            errorArchivePage = SearchByKeyword(errorArchiveSearchReq.getType(), keyword, pageable);
        } else if (keyword.isEmpty()) { // 카테고리 id로만  검색
            errorArchivePage = errorArchiveReository.findAllByCategoryId(categoryId, pageable);
        } else {
            errorArchivePage = SearchByKeywordAndCategory(errorArchiveSearchReq.getType(), keyword, categoryId, pageable);
        }

        return getListErrorArchiveRes(errorArchivePage);
    }

    private void ValidateSearchReq(GetErrorArchiveSearchReq errorArchiveSearchReq) { // request 값들의 유효성 검사
        String keyword = errorArchiveSearchReq.getKeyword().strip().toLowerCase();
        if ((keyword.isEmpty()) // 검색어도 없고, 카테고리도 선택되어 있지 않으면
                && (errorArchiveSearchReq.getCategoryId() == null || errorArchiveSearchReq.getCategoryId() == 0)) {
            throw new InvalidErrorBoardException(BaseResponseStatus.ERRORARCHIVE_SEARCH_EMPTY_REQUEST);
        }

        if (!SEARCH_TYPE.contains(errorArchiveSearchReq.getType())) { // type이 유효하지 않으면
            throw new InvalidErrorBoardException(BaseResponseStatus.ERRORARCHIVE_INVALID_SEARCH_TYPE);
        }

        if (errorArchiveSearchReq.getCategoryId() != null && errorArchiveSearchReq.getCategoryId() != 0) { // 카테고리 ID가 유효하지 않으면
            categoryRepository.findById(errorArchiveSearchReq.getCategoryId()).orElseThrow(() -> new InvalidCategoryException(BaseResponseStatus.CATEGORY_NOT_FOUND_CATEGORY));
        }
    }

    private Page<ErrorArchive> SearchByKeyword(String type, String keyword, Pageable pageable) {
        if (type.equals("tc")) {
            return errorArchiveReository.findAllByKeyword(keyword, pageable);
        } else if (type.equals("t")) {
            return errorArchiveReository.findAllByTitleIsContainingIgnoreCase(keyword, pageable);
        }
        return errorArchiveReository.findAllByContentIsContainingIgnoreCase(keyword, pageable);
    }

    private Page<ErrorArchive> SearchByKeywordAndCategory(String type, String keyword, Long categoryId, Pageable pageable) {
        if (type.equals("tc")) {
            return errorArchiveReository.findAllByKeywordAndCategory(keyword, categoryId, pageable);
        } else if (type.equals("t")) {
            return errorArchiveReository.findAllByTitleIsContainingIgnoreCaseAndCategoryId(keyword, categoryId, pageable);
        }
        return errorArchiveReository.findAllByContentIsContainingIgnoreCaseAndCategoryId(keyword, categoryId, pageable);
    }

    private static List<ListErrorArchiveRes> getListErrorArchiveRes(Page<ErrorArchive> errorArchivePage) {
        List<ListErrorArchiveRes> errorArchiveResList = new ArrayList<>();
        for (ErrorArchive errorArchive : errorArchivePage) {
            User user = errorArchive.getUser();
            Category category = errorArchive.getCategory();
            errorArchiveResList.add(ListErrorArchiveRes.builder()
                    .id(errorArchive.getId())
                    .title(errorArchive.getTitle())
                    .content(errorArchive.getContent())
                    .likeCnt(errorArchive.getLikeCount())
                    .createdAt(errorArchive.getCreatedAt().toString())
                    .totalPage(errorArchivePage.getTotalPages())
                    .nickname(user.getNickname())
                    .grade(user.getGrade())
                    .profileImg(user.getProfileImg())
                    .superCategory(category.getSuperCategory() != null ? category.getSuperCategory().getCategoryName() : category.getCategoryName())
                    .subCategory(category.getSuperCategory() != null ? category.getCategoryName() : null)
                    .build());
        }
        return errorArchiveResList;
    }

}
