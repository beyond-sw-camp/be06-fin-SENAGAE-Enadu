package org.example.backend.Main.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.search.MultiSearchRequest;
import org.elasticsearch.action.search.MultiSearchResponse;
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
import org.example.backend.Common.ChosungChecker;
import org.example.backend.ErrorArchive.Model.Doc.ErrorArchive;
import org.example.backend.ErrorArchive.Model.Res.ListErrorArchiveRes;
import org.example.backend.Exception.custom.InvalidMainException;
import org.example.backend.Main.Model.Res.GetMainSearchRes;
import org.example.backend.Qna.model.Doc.QnaBoard;
import org.example.backend.Qna.model.Res.GetQnaListRes;
import org.example.backend.Wiki.Model.Doc.Wiki;
import org.example.backend.Wiki.Model.Res.WikiListRes;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MainElasticSearchService {
    private final RestHighLevelClient restHighLevelClient;
    private final ObjectMapper objectMapper;
    // 변경된 mainSearch 메서드
    public GetMainSearchRes mainSearch(Integer wikiSize, Integer errorArchiveSize, Integer qnaSize, String keyword) throws IOException {
        validateSearchReq(keyword);

        // 각 인덱스별 BoolQueryBuilder 생성
        BoolQueryBuilder wikiQuery = QueryBuilders.boolQuery();
        BoolQueryBuilder errorArchiveQuery = QueryBuilders.boolQuery();
        BoolQueryBuilder qnaQuery = QueryBuilders.boolQuery();

        // 공통 검색 조건 설정
        // 초성일때, 제목 -> 자소, content -> 노리로 쓰므로, type 추가!
        setMainKeywordQuery(keyword, wikiQuery, "tc");
        setMainKeywordQuery(keyword, errorArchiveQuery, "tc");
        setMainKeywordQuery(keyword, qnaQuery, "tc");


        // 에러 아카이브, qna enable 조건 설정
        setEnableQuery(errorArchiveQuery);
        setEnableQuery(qnaQuery);

        // qan resolved 조건 설정
        setQnaResolvedQuery(qnaQuery);

        // MultiSearchRequest 생성
        MultiSearchRequest multiSearchRequest = new MultiSearchRequest();

        // 각 인덱스에 대해 SearchRequest 생성 및 추가
        SearchRequest wikiSearchRequest = new SearchRequest("wiki");
        wikiSearchRequest.source(createSearchSourceBuilder(wikiQuery, wikiSize));

        SearchRequest errorArchiveSearchRequest = new SearchRequest("error_archive");
        errorArchiveSearchRequest.source(createSearchSourceBuilder(errorArchiveQuery, errorArchiveSize));

        SearchRequest qnaSearchRequest = new SearchRequest("qna_board");
        qnaSearchRequest.source(createSearchSourceBuilder(qnaQuery, qnaSize));

        multiSearchRequest.add(wikiSearchRequest);
        multiSearchRequest.add(errorArchiveSearchRequest);
        multiSearchRequest.add(qnaSearchRequest);

        // MultiSearchResponse로 여러 검색 결과를 받아옴
        MultiSearchResponse multiSearchResponse = restHighLevelClient.msearch(multiSearchRequest, RequestOptions.DEFAULT);

        // 각 검색 결과를 기반으로 응답 객체 생성
        return makeAllRes(wikiSize, errorArchiveSize, qnaSize, multiSearchResponse);
    }

    // 각 인덱스의 SearchSourceBuilder를 생성하는 메서드
    private SearchSourceBuilder createSearchSourceBuilder(BoolQueryBuilder queryBuilder, int size) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(queryBuilder);
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(size);
        searchSourceBuilder.sort("created_at", SortOrder.DESC);
        return searchSourceBuilder;
    }

    private void validateSearchReq(String keyword){
        // 키워드가 비어있으면
        if(keyword == null || keyword.isBlank()) {
            throw new InvalidMainException(BaseResponseStatus.MAIN_SEARCH_EMPTY_REQUEST);
        }
    }
    // 초성 검색 메소드 추가
    private static void setChosungQuery(String keyword, BoolQueryBuilder boolQueryBuilder, String type) {
        // 초성으로만 구성된 경우 초성 검색 처리
            // 제목 검색일 경우, jaso 분석기로 검색
                MatchQueryBuilder jasoTitleQuery = QueryBuilders.matchQuery("title.jaso", keyword).fuzziness(Fuzziness.AUTO);;
                boolQueryBuilder.should(jasoTitleQuery);

            // 내용 검색일 경우, nori 분석기로 검색
                MatchQueryBuilder noriContentQuery = QueryBuilders.matchQuery("content", keyword).fuzziness(Fuzziness.AUTO);;
                boolQueryBuilder.should(noriContentQuery);
    }
    // 검색어가 입력되었을 때 초성 검색 포함한 메소드 수정
    private static void setMainKeywordQuery(String keyword, BoolQueryBuilder boolQueryBuilder, String type) {
            boolQueryBuilder.minimumShouldMatch(1);
            if(ChosungChecker.isChosungOnly(keyword)){
                // 초성 검색 처리
                setChosungQuery(keyword, boolQueryBuilder, type);
            } else {
                // 일반 검색 처리
                setKeyWordByType(keyword, boolQueryBuilder);
            }
    }
    // 검색어를 제목이나 본문에서 검색하는 쿼리문 생성 후 boolQueryBuilder에 추가
    private static void setKeyWordByType(String keyword, BoolQueryBuilder boolQueryBuilder){
            // 제목에 대한 검색 조건
            MatchPhraseQueryBuilder titleQueryBuilder = QueryBuilders.matchPhraseQuery("title.nori", keyword).slop(2);
            boolQueryBuilder.should(titleQueryBuilder);
            // 내용에 대한 검색 조건
            MatchQueryBuilder contentQueryBuilder = QueryBuilders.matchQuery("content", keyword)
                    .minimumShouldMatch("75%").fuzziness(Fuzziness.AUTO);
            boolQueryBuilder.should(contentQueryBuilder);
    }
    // enable이 true인 위키,에러 아카이브,qan만 검색 결과에 포함되도록
    private static void setEnableQuery(BoolQueryBuilder boolQueryBuilder){
        boolQueryBuilder.filter(QueryBuilders.matchQuery("enable", true));
    }
    // qna resolved가 true인것만 검색 결과에 포함되도록
    private static void setQnaResolvedQuery(BoolQueryBuilder boolQueryBuilder){
        BoolQueryBuilder resovledQuery = QueryBuilders.boolQuery()
                .mustNot(QueryBuilders.matchQuery("resolved",2));
        boolQueryBuilder.filter(resovledQuery);
    }

    // MultiSearchResponse에서 각각의 검색 결과를 처리하는 메서드
    private GetMainSearchRes makeAllRes(Integer wikiSize, Integer errorArchiveSize, Integer qnaSize, MultiSearchResponse multiSearchResponse) throws JsonProcessingException {
        // 각각의 리스트 생성
        List<GetQnaListRes> qnaListRes = new ArrayList<>();
        List<ListErrorArchiveRes> listErrorArchiveRes = new ArrayList<>();
        List<WikiListRes> wikiListResListRes = new ArrayList<>();

        // 각 검색 응답을 분리하여 처리
        for (int i = 0; i < multiSearchResponse.getResponses().length; i++) {
            MultiSearchResponse.Item item = multiSearchResponse.getResponses()[i];
            SearchResponse searchResponse = item.getResponse();

            // 해당 인덱스의 검색 결과를 처리
            for (SearchHit hit : searchResponse.getHits().getHits()) {
                if (i == 0 && hit.getIndex().equals("wiki")) {
                    Wiki wiki = objectMapper.readValue(hit.getSourceAsString(), Wiki.class);
                    wikiListResListRes.add(WikiListRes.builder()
                            .id(wiki.getId())
                            .title(wiki.getTitle())
                            .content(wiki.getContent())
                            .category(wiki.getCategoryName())
                            .createdAt(wiki.getCreatedAt())
                            .thumbnail(wiki.getThumbnailImgUrl())
                            .build());
                } else if (i == 1 && hit.getIndex().equals("error_archive")) {
                    ErrorArchive errorArchive = objectMapper.readValue(hit.getSourceAsString(), ErrorArchive.class);
                    listErrorArchiveRes.add(ListErrorArchiveRes.builder()
                            .id(errorArchive.getId())
                            .title(errorArchive.getTitle())
                            .content(errorArchive.getContent())
                            .profileImg(errorArchive.getProfileImg())
                            .nickname(errorArchive.getNickname())
                            .superCategory(errorArchive.getSuperCategoryName())
                            .subCategory(errorArchive.getSubCategoryName())
                            .createdAt(errorArchive.getCreatedAt().toString())
                            .grade(errorArchive.getGrade())
                            .build());
                } else if (i == 2 && hit.getIndex().equals("qna_board")) {
                    QnaBoard qnaBoard = objectMapper.readValue(hit.getSourceAsString(), QnaBoard.class);
                    qnaListRes.add(GetQnaListRes.builder()
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
                            .build());
                }
            }
        }

        // GetMainSearchRes 객체 생성 및 리스트 설정
        return GetMainSearchRes.builder()
                .qnaListResList(qnaListRes)
                .errorArchiveResList(listErrorArchiveRes)
                .wikiListResList(wikiListResListRes)
                .build();
    }

}
