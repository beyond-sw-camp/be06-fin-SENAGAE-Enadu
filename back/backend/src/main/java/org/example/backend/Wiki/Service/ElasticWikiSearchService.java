package org.example.backend.Wiki.Service;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.example.backend.Common.BaseResponseStatus;
import org.example.backend.Wiki.Model.Doc.Wiki;
import org.example.backend.Wiki.Model.Req.GetWikiSearchReq;
import org.example.backend.Wiki.Model.Res.WikiListRes;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.example.backend.Exception.custom.InvalidWikiException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Qualifier("WikiElasticSearch")
@RequiredArgsConstructor
//es에서 검색
public class ElasticWikiSearchService implements WikiSearchService {

    private final RestHighLevelClient restHighLevelClient;
    private final ObjectMapper objectMapper;
    private final static List<String> SEARCH_TYPE = List.of("tc", "t", "c");

    @Override
    public List<WikiListRes> search(GetWikiSearchReq getWikiSearchReq) throws IOException {
        ValidateSearchReq(getWikiSearchReq);

        if (getWikiSearchReq.getPage() == null) {
            getWikiSearchReq.setPage(0);
        }
        if (getWikiSearchReq.getSize() == null) {
            getWikiSearchReq.setSize(16);
        }

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery(); // bool 쿼리를 사용하면 그 안에 다른 쿼리들을 넣는 식으로 사용이 가능!

        if (getWikiSearchReq.getKeyword() != null && !getWikiSearchReq.getKeyword().isEmpty()) { // 키워드 값이 있는데
            String keyword = getWikiSearchReq.getKeyword().toLowerCase(); // 다 소문자 변환

            if ("t".equals(getWikiSearchReq.getType())) { // 제목 검색
                boolQuery.must(QueryBuilders.matchQuery("title", keyword));
            } else if ("c".equals(getWikiSearchReq.getType())) { // 내용 검색
                boolQuery.must(QueryBuilders.matchQuery("content", keyword));
            } else if ("tc".equals(getWikiSearchReq.getType())) { // 제목+내용 검색
                boolQuery.must(QueryBuilders.multiMatchQuery(keyword, "title", "content"));
            }
        }

        if (getWikiSearchReq.getCategoryId() != null && getWikiSearchReq.getCategoryId() != 0) { // 카테고리 값이 있으면
            boolQuery.filter(QueryBuilders.termQuery("category_id", getWikiSearchReq.getCategoryId()));
        }

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder() // Elasticsearch에 보낼 검색 쿼리를 설정하는 빌더
                .query(boolQuery)
                .from(getWikiSearchReq.getPage() * getWikiSearchReq.getSize())  // 페이지 설정
                .size(getWikiSearchReq.getSize())  // 페이지 크기 설정
                .sort("created_at", SortOrder.DESC);  // 최신순 정렬

        // SearchRequest = Elasticsearch에서 검색 요청을 정의하는 객체
        SearchRequest searchRequest = new SearchRequest("wiki");  // 인덱스 설정
        searchRequest.source(searchSourceBuilder);

        // Elasticsearch 검색 실행
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        // 검색 결과를 WikiListRes로 변환
        return searchResponseToWikiListRes(searchResponse);
    }

    // 유효성 검사
    private void ValidateSearchReq(GetWikiSearchReq getWikiSearchReq) {
        String keyword = getWikiSearchReq.getKeyword().toLowerCase().replaceAll("\\s", "");
        if ((keyword.isEmpty())
                && (getWikiSearchReq.getCategoryId() == null || getWikiSearchReq.getCategoryId() == 0)) {
            throw new InvalidWikiException(BaseResponseStatus.WIKI_SEARCH_EMPTY_REQUEST);
        }

        if (!SEARCH_TYPE.contains(getWikiSearchReq.getType())) {
            throw new InvalidWikiException(BaseResponseStatus.WIKI_INVALID_SEARCH_TYPE);
        }

        if (getWikiSearchReq.getCategoryId() != null && getWikiSearchReq.getCategoryId() != 0) {
            // 카테고리 유효성 체크 (categoryRepository를 추가해서 처리하거나 별도의 로직 추가)
        }
    }

    // SearchResponse를 WikiListRes로 변환하는 메서드
    private List<WikiListRes> searchResponseToWikiListRes(SearchResponse searchResponse) {
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
                            .build();
                })
                .collect(Collectors.toList());
    }
}
