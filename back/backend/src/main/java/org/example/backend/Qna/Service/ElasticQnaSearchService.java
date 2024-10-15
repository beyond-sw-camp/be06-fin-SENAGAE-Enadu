package org.example.backend.Qna.Service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchPhraseQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
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

@Service("QnaElasticSearch")
@RequiredArgsConstructor
public class ElasticQnaSearchService implements QnaSearchService {

    private final static List<String> SEARCH_TYPE = List.of("tc", "t", "c");
    private final static List<String> RESOLVE_TYPE = List.of("ALL", "RESOLVED", "UNSOLVED");
    private final static List<String> SORT_TYPE = List.of("latest", "like", "accuracy");
    private final static String INDEX = "qna_board";
    private final RestHighLevelClient restHighLevelClient;
    private final ObjectMapper objectMapper;


    @Override
    public List<GetQnaListRes> getQnaSearch(GetQnaSearchReq getQnaSearchReq) throws IOException {
        validateSearchReq(getQnaSearchReq); // 요청값 유효성 검사
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        setQnaCategoryQuery(getQnaSearchReq, boolQueryBuilder);

        setQnaKeywordQuery(getQnaSearchReq, boolQueryBuilder);

        setQnaResolvedQuery(getQnaSearchReq, boolQueryBuilder);

        setQnaEnableQuery(boolQueryBuilder);

        SearchResponse searchResponse = getSearchResponse(getQnaSearchReq, boolQueryBuilder);
        return makeQnaListRes(getQnaSearchReq, searchResponse);
    }

    private void validateSearchReq(GetQnaSearchReq getQnaSearchReq) { // request 값들의 유효성 검사
        String keyword = getQnaSearchReq.getKeyword();
        // 검색어도 없고, 카테고리도 선택되어 있지 않으면
        if (keyword.isBlank() && (getQnaSearchReq.getCategoryId() == null || getQnaSearchReq.getCategoryId() == 0)) {
            throw new InvalidQnaException(BaseResponseStatus.QNA_SEARCH_EMPTY_REQUEST);
        }

        if (getQnaSearchReq.getPage() == null) {
            getQnaSearchReq.setPage(0);
        }

        if (getQnaSearchReq.getSize() == null) {
            getQnaSearchReq.setSize(12);
        }

        if (!SEARCH_TYPE.contains(getQnaSearchReq.getType())) { // type이 유효하지 않으면
            throw new InvalidQnaException(BaseResponseStatus.QNA_INVALID_SEARCH_TYPE);
        }

        if (!RESOLVE_TYPE.contains(getQnaSearchReq.getResolved())){
            throw new InvalidQnaException(BaseResponseStatus.QNA_INVALID_RESOLVE_TYPE);
        }

        if (!SORT_TYPE.contains(getQnaSearchReq.getSort())) {
            throw new InvalidQnaException(BaseResponseStatus.QNA_INVALID_SORT_TYPE);
        }
    }

    private static void setQnaCategoryQuery(GetQnaSearchReq getQnaSearchReq, BoolQueryBuilder boolQueryBuilder) {
        if (getQnaSearchReq.getCategoryId() != null) { // 카테고리가 선택되었을 때
            BoolQueryBuilder categoryFilter = QueryBuilders.boolQuery()
                    .should(QueryBuilders.termQuery("super_category_id", getQnaSearchReq.getCategoryId()))
                    .should(QueryBuilders.termQuery("sub_category_id", getQnaSearchReq.getCategoryId()));
            boolQueryBuilder.filter(categoryFilter);
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
            MatchQueryBuilder contentQueryBuilder = QueryBuilders.matchQuery("content", getQnaSearchReq.getKeyword()).minimumShouldMatch("75%").fuzziness(Fuzziness.AUTO);
            boolQueryBuilder.should(contentQueryBuilder);
        }
    }

    private static void setQnaResolvedQuery(GetQnaSearchReq getQnaSearchReq, BoolQueryBuilder boolQueryBuilder) {
        if (getQnaSearchReq.getResolved().equals("RESOLVED")) {
            boolQueryBuilder.filter(QueryBuilders.matchQuery("resolved", 0));
        } else if (getQnaSearchReq.getResolved().equals("UNSOLVED")) {
            boolQueryBuilder.filter(QueryBuilders.matchQuery("resolved", 1));
        } else {
            boolQueryBuilder.filter(QueryBuilders.boolQuery().mustNot(QueryBuilders.matchQuery("resolved", 2)));
        }
    }

    private static void setQnaEnableQuery(BoolQueryBuilder boolQueryBuilder) {
        MatchQueryBuilder enabledQueryBuilder = QueryBuilders.matchQuery("enable", true);
        boolQueryBuilder.filter(enabledQueryBuilder);
    }

    private SearchResponse getSearchResponse(GetQnaSearchReq getQnaSearchReq, BoolQueryBuilder boolQueryBuilder) throws IOException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(boolQueryBuilder);
        searchSourceBuilder.from(getQnaSearchReq.getPage() * getQnaSearchReq.getSize());
        searchSourceBuilder.size(getQnaSearchReq.getSize());

        setSortType(getQnaSearchReq, searchSourceBuilder);

        SearchRequest searchRequest = new SearchRequest(INDEX);
        searchRequest.source(searchSourceBuilder);
        return restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
    }

    private static void setSortType(GetQnaSearchReq getQnaSearchReq, SearchSourceBuilder searchSourceBuilder) {
        if (getQnaSearchReq.getSort().equals("latest")) {
            searchSourceBuilder.sort("created_at", SortOrder.DESC);
        } else if (getQnaSearchReq.getSort().equals("like")){
            searchSourceBuilder.sort("like_cnt", SortOrder.DESC);
        } // 정확도순 정렬
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
