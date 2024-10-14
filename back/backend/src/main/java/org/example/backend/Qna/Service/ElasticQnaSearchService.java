package org.example.backend.Qna.Service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.example.backend.Common.BaseResponseStatus;
import org.example.backend.Exception.custom.InvalidQnaException;
import org.example.backend.Qna.model.Doc.QnaBoard;
import org.example.backend.Qna.model.Res.GetQnaListRes;
import org.example.backend.Qna.model.req.GetQnaSearchReq;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ElasticQnaSearchService implements QnaSearchService {

    private final static List<String> SEARCH_TYPE = List.of("tc", "t", "c");
    private final static String INDEX = "qna_board";
    private final RestHighLevelClient restHighLevelClient;
    private final ObjectMapper objectMapper;


    @Override
    public List<GetQnaListRes> getQnaSearch(GetQnaSearchReq getQnaSearchReq) throws IOException {
        validateSearchReq(getQnaSearchReq); // 요청값 유효성 검사
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        setQnaCategoryQuery(getQnaSearchReq, boolQueryBuilder);

        setQnaKeywordQuery(getQnaSearchReq, boolQueryBuilder);

        setQnaEnableQuery(boolQueryBuilder);

        setQnaResolvedQuery(getQnaSearchReq, boolQueryBuilder);

        SearchResponse searchResponse = getSearchResponse(getQnaSearchReq, boolQueryBuilder);
        return makeQnaListRes(getQnaSearchReq, searchResponse);
    }

    private static void setQnaEnableQuery(BoolQueryBuilder boolQueryBuilder) {
        MatchQueryBuilder enabledQueryBuilder = QueryBuilders.matchQuery("enable", true);
        boolQueryBuilder.must(enabledQueryBuilder);
    }

    private void validateSearchReq(GetQnaSearchReq getQnaSearchReq) { // request 값들의 유효성 검사
        String keyword = getQnaSearchReq.getKeyword().strip();
        if (keyword.isEmpty() // 검색어도 없고, 카테고리도 선택되어 있지 않으면
                && (getQnaSearchReq.getCategoryId() == null || getQnaSearchReq.getCategoryId() == 0)) {
            throw new InvalidQnaException(BaseResponseStatus.QNA_SEARCH_EMPTY_REQUEST);
        }

        if (!SEARCH_TYPE.contains(getQnaSearchReq.getType())) { // type이 유효하지 않으면
            throw new InvalidQnaException(BaseResponseStatus.QNA_INVALID_SEARCH_TYPE);
        }
    }

    private static void setQnaCategoryQuery(GetQnaSearchReq getQnaSearchReq, BoolQueryBuilder boolQueryBuilder) {
        if (getQnaSearchReq.getCategoryId() != null) { // 카테고리가 선택되었을 때
            QueryStringQueryBuilder categoryQueryBuilder = QueryBuilders
                    .queryStringQuery(getQnaSearchReq.getCategoryId().toString())
                    .field("super_category_id")
                    .field("sub_category_id");
            boolQueryBuilder.must(categoryQueryBuilder);
        }
    }

    private static void setQnaKeywordQuery(GetQnaSearchReq getQnaSearchReq, BoolQueryBuilder boolQueryBuilder) {
        if (getQnaSearchReq.getKeyword() != null && !getQnaSearchReq.getKeyword().isBlank()) { // 검색어가 입력되었을 때
            boolQueryBuilder.minimumShouldMatch(1);
            setKeywordByType(getQnaSearchReq, boolQueryBuilder);
        }
    }

    private static void setKeywordByType(GetQnaSearchReq getQnaSearchReq, BoolQueryBuilder boolQueryBuilder) {
        if (getQnaSearchReq.getType().contains("t")) {
            MatchPhraseQueryBuilder titleQueryBuilder = QueryBuilders.matchPhraseQuery("title", getQnaSearchReq.getKeyword()).slop(2);
            boolQueryBuilder.should(titleQueryBuilder);

        }
        if (getQnaSearchReq.getType().contains("c")) {
            MatchPhraseQueryBuilder contentQueryBuilder = QueryBuilders.matchPhraseQuery("content", getQnaSearchReq.getKeyword()).slop(2);
            boolQueryBuilder.should(contentQueryBuilder);
        }
    }

    private static void setQnaResolvedQuery(GetQnaSearchReq getQnaSearchReq, BoolQueryBuilder boolQueryBuilder) {
        if (getQnaSearchReq.getResolved().equals("RESOLVED")){
            boolQueryBuilder.must(QueryBuilders.matchQuery("resolved", 0));
        } else if (getQnaSearchReq.getResolved().equals("UNSOLVED")) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("resolved", 1));
        } else {
            boolQueryBuilder.mustNot(QueryBuilders.matchQuery("resolved", 2));
        }
    }

    private SearchResponse getSearchResponse(GetQnaSearchReq getQnaSearchReq, BoolQueryBuilder boolQueryBuilder) throws IOException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(boolQueryBuilder);
        searchSourceBuilder.from(getQnaSearchReq.getPage() * getQnaSearchReq.getSize());
        searchSourceBuilder.size(getQnaSearchReq.getSize());
        if (getQnaSearchReq.getSort().equals("latest")) {
            searchSourceBuilder.sort("created_at", SortOrder.DESC);
        } else {
            searchSourceBuilder.sort("like_cnt", SortOrder.DESC);
        }
        SearchRequest searchRequest = new SearchRequest(INDEX);
        searchRequest.source(searchSourceBuilder);
        return restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
    }

    private List<GetQnaListRes> makeQnaListRes(GetQnaSearchReq getQnaSearchReq, SearchResponse searchResponse) throws JsonProcessingException {
        List<GetQnaListRes> getQnaListRes = new ArrayList<>();

        long totalDocsCount = searchResponse.getHits().getTotalHits().value;
        Integer totalPage = (int) totalDocsCount / getQnaSearchReq.getSize() + (totalDocsCount % getQnaSearchReq.getSize() == 0 ? 0 : 1);

        for (SearchHit hit : searchResponse.getHits().getHits()) {
            QnaBoard qnaBoard = objectMapper.readValue(hit.getSourceAsString(), QnaBoard.class);
            getQnaListRes.add(GetQnaListRes.builder()
                    .id(qnaBoard.getId())
                    .title(qnaBoard.getTitle())
                    .superCategoryName(qnaBoard.getSuperCategoryName())
                    .subCategoryName(qnaBoard.getSubCategoryName())
                    .likeCnt(qnaBoard.getLikeCnt())
                    .createdAt(qnaBoard.getCreatedAt())
                    .answerCnt(qnaBoard.getAnswerCnt())
                    .nickname(qnaBoard.getNickname())
                    .profileImage(qnaBoard.getProfileImg())
                    .grade(qnaBoard.getGrade())
                    .totalPage(totalPage)
                    .build());
        }
        return getQnaListRes;
    }
}
