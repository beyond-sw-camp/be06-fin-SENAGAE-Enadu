package org.example.backend.Wiki.Service;

import com.example.common.Category.Repository.CategoryRepository;
import com.example.common.Wiki.Model.Entity.Wiki;
import com.example.common.Wiki.Repository.WikiRepository;
import lombok.RequiredArgsConstructor;
import org.example.backend.Common.BaseResponseStatus;
import org.example.backend.Exception.custom.InvalidCategoryException;
import org.example.backend.Exception.custom.InvalidWikiException;
import org.example.backend.Wiki.Model.Req.GetWikiSearchReq;
import org.example.backend.Wiki.Model.Res.WikiListRes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("WikiDbSearch")
@RequiredArgsConstructor
//db에서 검색
public class DbWikiSearchService implements WikiSearchService {
    private final CategoryRepository categoryRepository;
    private final WikiRepository wikiRepository;
    private final static List<String> SEARCH_TYPE = List.of("tc", "t", "c");

    @Override
    public List<WikiListRes> search(GetWikiSearchReq getWikiSearchReq) {
        ValidateSearchReq(getWikiSearchReq);

        if (getWikiSearchReq.getPage() == null) {
            getWikiSearchReq.setPage(0);
        }
        if (getWikiSearchReq.getSize() == null || getWikiSearchReq.getSize() == 0) {
            getWikiSearchReq.setSize(15);
        }

        Pageable pageable = PageRequest.of(getWikiSearchReq.getPage(), getWikiSearchReq.getSize(), Sort.by(Sort.Direction.DESC, "latestWiki.createdAt"));
        Page<Wiki> wikiPage;

        Long categoryId = getWikiSearchReq.getCategoryId();
        String type = getWikiSearchReq.getType();
        String keyword = getWikiSearchReq.getKeyword().toLowerCase().replaceAll("\\s", "");

        if (categoryId == null || categoryId == 0) { // 검색어로만 검색
            wikiPage = SearchByKeyword(getWikiSearchReq.getType(), keyword, pageable);
        } else if (keyword.isEmpty()) { // 카테고리 id로만 검색
            wikiPage = wikiRepository.findAllByCategoryId(categoryId, pageable);
        } else {   //검색어 + 카테고리 검색
            wikiPage = SearchByKeywordAndCategory(type,keyword,categoryId,pageable);
    }
        return getListWikiRes(wikiPage);
}

private void ValidateSearchReq(GetWikiSearchReq getWikiSearchReq) { // 유효성 확인 메서드
    String keyword = getWikiSearchReq.getKeyword().toLowerCase().replaceAll("\\s", "");
    if ((keyword.isEmpty()) // 검색어 && 카테고리 선택 X
            && (getWikiSearchReq.getCategoryId() == null || getWikiSearchReq.getCategoryId() == 0)) {
        throw new InvalidWikiException(BaseResponseStatus.WIKI_SEARCH_EMPTY_REQUEST);
    }

    if (!SEARCH_TYPE.contains(getWikiSearchReq.getType())) { // type이 유효하지 않으면
        throw new InvalidWikiException(BaseResponseStatus.WIKI_INVALID_SEARCH_TYPE);
    }

    if (getWikiSearchReq.getCategoryId() != null && getWikiSearchReq.getCategoryId() != 0) { // 카테고리 ID가 유효하지 않으면
        categoryRepository.findById(getWikiSearchReq.getCategoryId()).orElseThrow(() -> new InvalidCategoryException(BaseResponseStatus.CATEGORY_NOT_FOUND_CATEGORY));
    }
}

private Page<Wiki> SearchByKeyword(String type, String keyword, Pageable pageable) { // 키워드로만 검색
    if (type.equals("tc")) {
        return wikiRepository.findAllByKeyword(keyword, pageable);
    } else if (type.equals("t")) {
        return wikiRepository.findAllByTitleIsContainingIgnoreCase(keyword, pageable);
    }
    return wikiRepository.findAllByLatestWikiContentIsContainingIgnoreCase(keyword, pageable);
}

private Page<Wiki> SearchByKeywordAndCategory(String type, String keyword, Long categoryId, Pageable pageable) { // 카테고리 + 키워드로 검색
    if (type.equals("tc")) {
        return wikiRepository.findAllByKeywordAndCategory(keyword, categoryId, pageable);
    } else if (type.equals("t")) {
        return wikiRepository.findAllByTitleIsContainingIgnoreCaseAndCategoryId(keyword, categoryId, pageable);
    }
    return wikiRepository.findAllByLatestWikiContentIsContainingIgnoreCaseAndCategoryId(keyword, categoryId, pageable);
}

private static List<WikiListRes> getListWikiRes(Page<Wiki> wikiPage) {
    List<WikiListRes> wikiResList = new ArrayList<>();
    for (Wiki wiki : wikiPage) {
        wikiResList.add(WikiListRes.builder()
                .id(wiki.getId())
                .title(wiki.getTitle())
                .category(wiki.getCategory().getCategoryName())
                .content(wiki.getLatestWiki().getContent())
                .thumbnail(wiki.getLatestWiki().getThumbnailImgUrl())
                .createdAt(wiki.getLatestWiki().getCreatedAt())
                .totalPages(wikiPage.getTotalPages())
                .build());
    }
    return wikiResList;
}


}