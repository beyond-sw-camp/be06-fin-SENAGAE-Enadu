package org.example.backend.ErrorArchive.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.example.backend.Common.BaseResponseStatus;
import org.example.backend.ErrorArchive.Model.Doc.ErrorArchive;
import org.example.backend.ErrorArchive.Model.Req.GetErrorArchiveSearchReq;
import org.example.backend.ErrorArchive.Model.Res.ListErrorArchiveRes;
import org.example.backend.Exception.custom.InvalidErrorBoardException;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service("ErrorarchiveElasticService")
@RequiredArgsConstructor
public class ErrorArchiveElasticSearchService implements ErrorArchiveSearchService {
    private final RestHighLevelClient restHighLevelClient; // RestHighLevelClient 사용
    private final ObjectMapper objectMapper;
    private final static String INDEX = "error_archive";
    private final static List<String> SEARCH_TYPE = List.of("tc", "t", "c");
    private final static List<String> SORT_TYPE = List.of("latest","like","accuracy");

    public List<ListErrorArchiveRes> errorArchiveSearch(GetErrorArchiveSearchReq getErrorArchiveSearchReq) throws IOException {
        validateSearchReq(getErrorArchiveSearchReq);

        // BoolQueryBuilder 사용
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        setErrorArchiveCategoryQuery(getErrorArchiveSearchReq,boolQueryBuilder);
        setErrorArchiveKeywordQuery(getErrorArchiveSearchReq,boolQueryBuilder);
        setErrorArchiveEnableQuery(boolQueryBuilder);

        SearchResponse searchResponse = getSearchResponse(getErrorArchiveSearchReq, boolQueryBuilder);
        return makeErrorArchiveRes(getErrorArchiveSearchReq, searchResponse);
    }
    // 유효성 검사
    private void validateSearchReq(GetErrorArchiveSearchReq getErrorArchiveSearchReq) {
        // 검색어가 null이 아니면 공백을 제거하고, 키워드를 저장, null이면 "" 처리
        String keyword = getErrorArchiveSearchReq.getKeyword();
        // 검색어와 카테고리가 없으면
        if ((keyword == null || keyword.isBlank()) && (getErrorArchiveSearchReq.getCategoryId() == null || getErrorArchiveSearchReq.getCategoryId() == 0)) {
            throw new InvalidErrorBoardException(BaseResponseStatus.ERRORARCHIVE_SEARCH_EMPTY_REQUEST);
        }
        if (getErrorArchiveSearchReq.getPage() == null){
            getErrorArchiveSearchReq.setPage(0);
        }
        if(getErrorArchiveSearchReq.getSize() == null){
            getErrorArchiveSearchReq.setSize(16);
        }
        // 타입이 유효하지 않으면
        if (!SEARCH_TYPE.contains(getErrorArchiveSearchReq.getType())) {
            throw new InvalidErrorBoardException(BaseResponseStatus.ERRORARCHIVE_SEARCH_EMPTY_REQUEST);
        }
        // 정렬이 유효하지 않으면
        if(!SORT_TYPE.contains(getErrorArchiveSearchReq.getSort())){
            throw new InvalidErrorBoardException(BaseResponseStatus.ERRORARCHIVE_INVALID_SORT_TYPE);
        }
    }
    // 카테고리가 있으면 상위, 하위id로 검색 쿼리 추가
    private static void setErrorArchiveCategoryQuery(GetErrorArchiveSearchReq getErrorArchiveSearchReq, BoolQueryBuilder boolQueryBuilder){
        if (getErrorArchiveSearchReq.getCategoryId() != null){
            BoolQueryBuilder categoryFilter = QueryBuilders.boolQuery()
                    .should(QueryBuilders.termQuery("super_category_id", getErrorArchiveSearchReq.getCategoryId()))
                    .should(QueryBuilders.termQuery("sub_category_id", getErrorArchiveSearchReq.getCategoryId()));
            boolQueryBuilder.filter(categoryFilter);
        }
    }
    // 검색어가 입력되었을 떄
    private static void setErrorArchiveKeywordQuery(GetErrorArchiveSearchReq getErrorArchiveSearchReq, BoolQueryBuilder boolQueryBuilder){
        if(getErrorArchiveSearchReq.getKeyword()!=null && !getErrorArchiveSearchReq.getKeyword().isBlank()){
            setKeyWordByType(getErrorArchiveSearchReq, boolQueryBuilder);
        }
    }

    // 검색타입에 따라 검색어를 제목이나 본문에서 검색하는 쿼리문 생성후 boolQueryBuilder에 추가
    private static void setKeyWordByType(GetErrorArchiveSearchReq getErrorArchiveSearchReq, BoolQueryBuilder boolQueryBuilder){
        if(getErrorArchiveSearchReq.getType().contains("t")){
            MatchPhraseQueryBuilder titleQueryBuilder = QueryBuilders.matchPhraseQuery("title", getErrorArchiveSearchReq.getKeyword()).slop(2);
            boolQueryBuilder.should(titleQueryBuilder);
        }
        if(getErrorArchiveSearchReq.getType().contains("c")){
            MatchQueryBuilder contentQueryBuilder = QueryBuilders.matchQuery("content", getErrorArchiveSearchReq.getKeyword()).minimumShouldMatch("75%").fuzziness(Fuzziness.AUTO);
            boolQueryBuilder.should(contentQueryBuilder);
        }
    }

    // enalbe이 true인 에러 아카이브만 검색에 포함되도록
    private static void setErrorArchiveEnableQuery(BoolQueryBuilder boolQueryBuilder) {
        MatchQueryBuilder enabledQueryBuilder = QueryBuilders.matchQuery("enable", true);
        boolQueryBuilder.filter(enabledQueryBuilder);
    }
    private SearchResponse getSearchResponse(GetErrorArchiveSearchReq getErrorArchiveSearchReq, BoolQueryBuilder boolQueryBuilder) throws IOException{
        // 검색 요청 객체 생성
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(boolQueryBuilder);
        // 검색 결과 시작 지점
        searchSourceBuilder.from(getErrorArchiveSearchReq.getPage() * getErrorArchiveSearchReq.getSize());
        // 검색 결과 페이지 크기 설정
        searchSourceBuilder.size(getErrorArchiveSearchReq.getSize());
        if(getErrorArchiveSearchReq.getSort().equals("latest")){
            searchSourceBuilder.sort("created_at", SortOrder.DESC);
        } else if (getErrorArchiveSearchReq.getSort().equals("like")){
            searchSourceBuilder.sort("like_cnt", SortOrder.DESC);
        }
        // search()를 통해 검색 수행 후 응답값 받아옴
        SearchRequest searchRequest = new SearchRequest(INDEX);
        searchRequest.source(searchSourceBuilder);
        return restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
    }

    private List<ListErrorArchiveRes> makeErrorArchiveRes(GetErrorArchiveSearchReq getErrorArchiveSearchReq, SearchResponse searchResponse) throws JsonProcessingException {
        List<ListErrorArchiveRes> getErrorArchiveListRes = new ArrayList<>();
        long totalDocsCount = searchResponse.getHits().getTotalHits().value;
        Integer totalPage = (int) totalDocsCount / getErrorArchiveSearchReq.getSize() + (totalDocsCount % getErrorArchiveSearchReq.getSize() == 0 ? 0 : 1);
        for (SearchHit hit: searchResponse.getHits().getHits()){
            ErrorArchive errorArchive = objectMapper.readValue(hit.getSourceAsString(), ErrorArchive.class);
            getErrorArchiveListRes.add(ListErrorArchiveRes.builder()
                    .id(errorArchive.getId())
                    .title(errorArchive.getTitle())
                    .content(errorArchive.getContent())
                    .profileImg(errorArchive.getProfileImg())
                    .nickname(errorArchive.getNickname())
                    .superCategory(errorArchive.getSuperCategoryName())
                    .subCategory(errorArchive.getSubCategoryName())
                    .createdAt(errorArchive.getCreatedAt().toString())
                    .grade(errorArchive.getGrade())
                    .totalPage(totalPage)
                    .build());
        }
        return getErrorArchiveListRes;
    }
}
