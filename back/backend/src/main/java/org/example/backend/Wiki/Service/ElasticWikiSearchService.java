package org.example.backend.Wiki.Service;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.example.backend.Common.BaseResponseStatus;
import org.example.backend.Common.ChosungChecker;
import org.example.backend.Exception.custom.InvalidWikiException;
import org.example.backend.Wiki.Model.Doc.Wiki;
import org.example.backend.Wiki.Model.Req.GetWikiSearchReq;
import org.example.backend.Wiki.Model.Res.WikiListRes;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service("WikiElasticSearch")
@RequiredArgsConstructor
//es에서 검색
public class ElasticWikiSearchService implements WikiSearchService {

    private final RestHighLevelClient restHighLevelClient;
    private final ObjectMapper objectMapper;
    private final static List<String> SEARCH_TYPE = List.of("tc", "t", "c");
    private final static String INDEX = "wiki";

    @Override
    public List<WikiListRes> search(GetWikiSearchReq getWikiSearchReq) throws IOException {

        validateSearchReq(getWikiSearchReq);

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        setWikiKeywordQuery(getWikiSearchReq, boolQuery);

        setWikiCategoryQuery(getWikiSearchReq, boolQuery);

        SearchSourceBuilder searchSourceBuilder = getSearchSourceBuilder(getWikiSearchReq, boolQuery);

        // Elasticsearch에서 검색 요청
        SearchRequest searchRequest = new SearchRequest(INDEX);
        searchRequest.source(searchSourceBuilder);

        // Elasticsearch 검색 실행
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        long totalDocsCount = searchResponse.getHits().getTotalHits().value;
        Integer totalPage = (int) totalDocsCount / getWikiSearchReq.getSize() + (totalDocsCount % getWikiSearchReq.getSize() == 0 ? 0 : 1);

        // 검색 결과를 WikiListRes로 변환
        return searchResponseToWikiListRes(searchResponse, totalPage);
    }

    // 카테고리로 검색
    private static void setWikiCategoryQuery(GetWikiSearchReq getWikiSearchReq, BoolQueryBuilder boolQuery) {
        if (getWikiSearchReq.getCategoryId() != null && getWikiSearchReq.getCategoryId() != 0) {
            boolQuery.filter(QueryBuilders.termQuery("category_id", getWikiSearchReq.getCategoryId()));
        }
    }

    // 키워드 타입 조회
    private static void setWikiKeywordQuery(GetWikiSearchReq getWikiSearchReq, BoolQueryBuilder boolQuery) {
        if (getWikiSearchReq.getKeyword() != null && !getWikiSearchReq.getKeyword().isEmpty()) {
            boolQuery.minimumShouldMatch(1);

            if (ChosungChecker.isChosungOnly(getWikiSearchReq.getKeyword())) {
                setChosungBytype(getWikiSearchReq, boolQuery);
            } else {
                setKeywordByType(getWikiSearchReq, boolQuery);
            }
        }
    }

    // 초성 검색
    private static void setChosungBytype(GetWikiSearchReq getWikiSearchReq, BoolQueryBuilder boolQuery) {
        if ((getWikiSearchReq.getType()).contains("t")) {
            boolQuery.should(QueryBuilders.matchQuery("title.jaso", getWikiSearchReq.getKeyword()));
        }
        if ((getWikiSearchReq.getType()).contains("c")) {
            boolQuery.should(QueryBuilders.matchQuery("content", getWikiSearchReq.getKeyword()));
        }
    }

    // 일반 검색
    private static void setKeywordByType(GetWikiSearchReq getWikiSearchReq, BoolQueryBuilder boolQuery) {
        if ((getWikiSearchReq.getType()).contains("t")) { // 제목 검색
            boolQuery.should(QueryBuilders.matchPhraseQuery("title.nori", getWikiSearchReq.getKeyword()).slop(2));
        }
        if ((getWikiSearchReq.getType()).contains("c")) { // 내용 검색
            boolQuery.should(QueryBuilders.matchQuery("content", getWikiSearchReq.getKeyword()).minimumShouldMatch("75%").fuzziness(Fuzziness.AUTO));
        }
    }

    // Elasticsearch에 보낼 검색 쿼리 설정
    private static SearchSourceBuilder getSearchSourceBuilder(GetWikiSearchReq getWikiSearchReq, BoolQueryBuilder boolQuery) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder()
                .query(boolQuery)
                .from(getWikiSearchReq.getPage() * getWikiSearchReq.getSize())  // 페이지 설정
                .size(getWikiSearchReq.getSize())  // 페이지 크기 설정
                .sort("created_at", SortOrder.DESC);  // 최신순 정렬
        return searchSourceBuilder;
    }

    // 유효성 검사
    private void validateSearchReq(GetWikiSearchReq getWikiSearchReq) {
        if (getWikiSearchReq.getPage() == null) {
            getWikiSearchReq.setPage(0);
        }
        if (getWikiSearchReq.getSize() == null || getWikiSearchReq.getSize() == 0) {
            getWikiSearchReq.setSize(16);
        }

        String keyword = getWikiSearchReq.getKeyword().toLowerCase().replaceAll("\\s", "");
        // 검색어 && 카테고리 = X
        if ((keyword.isEmpty()) && (getWikiSearchReq.getCategoryId() == null || getWikiSearchReq.getCategoryId() == 0)) {
            throw new InvalidWikiException(BaseResponseStatus.WIKI_SEARCH_EMPTY_REQUEST);
        }

        if (!SEARCH_TYPE.contains(getWikiSearchReq.getType())) {
            throw new InvalidWikiException(BaseResponseStatus.WIKI_INVALID_SEARCH_TYPE);
        }

        if (ChosungChecker.isChosungOnly(getWikiSearchReq.getKeyword()) && keyword.length() == 1) {
            throw new InvalidWikiException(BaseResponseStatus.WIKI_INVALID_KEYWORD_TYPE);
        }
    }

    // SearchResponse를 WikiListRes로 변환하는 메서드
    private List<WikiListRes> searchResponseToWikiListRes(SearchResponse searchResponse, Integer totalPage) {
        return Arrays.stream(searchResponse.getHits().getHits())
                .map(hit -> {
                    Wiki wiki = objectMapper.convertValue(hit.getSourceAsMap(), Wiki.class);
                    return WikiListRes.builder()
                            .id(wiki.getId())
                            .title(wiki.getTitle())
                            .content(wiki.getContent())
                            .category(wiki.getCategoryName())
                            .createdAt(wiki.getCreatedAt())
                            .thumbnail(wiki.getThumbnailImgUrl())
                            .totalPages(totalPage)
                            .build();
                })
                .collect(Collectors.toList());
    }
}
