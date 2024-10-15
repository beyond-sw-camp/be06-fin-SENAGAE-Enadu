package org.example.backend.Main.Service;

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
import org.example.backend.ErrorArchive.Model.Doc.ErrorArchive;
import org.example.backend.ErrorArchive.Model.Res.ListErrorArchiveRes;
import org.example.backend.Exception.custom.InvalidMainException;
import org.example.backend.Main.Model.Req.GetMainSearchReq;
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
    private final static String[] INDEXS = new String[]{"wiki", "error_archive", "qna_board"};
    private final static String SEARCH_TYPE = "tc";
    public GetMainSearchRes mainSearch(GetMainSearchReq getMainSearchReq) throws IOException {
        validateSearchReq(getMainSearchReq);
        // BoolQueryBuilder 사용
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        setAllEnableQuery(boolQueryBuilder);
        setMainKeywordQuery(getMainSearchReq, boolQueryBuilder);
        setKeyWordByType(getMainSearchReq,boolQueryBuilder);

        SearchResponse searchResponse = getSearchResponse(getMainSearchReq, boolQueryBuilder);
        return makeAllRes(getMainSearchReq, searchResponse);
    }

    private void validateSearchReq(GetMainSearchReq getMainSearchReq){
        String keyword = getMainSearchReq.getKeyword();
        // 키워드가 비어있으면
        if(keyword == null || keyword.isBlank()) {
            throw new InvalidMainException(BaseResponseStatus.MAIN_SEARCH_EMPTY_REQUEST);
        }
        // 타입이 유효하지 않으면
        if(!SEARCH_TYPE.contains(getMainSearchReq.getType())){
            throw new InvalidMainException(BaseResponseStatus.MAIN_SEARCH_EMPTY_REQUEST);
        }
        if(getMainSearchReq.getPage() == null){
            getMainSearchReq.setPage(0);
        }
    }
    // 검색어가 입력되었을 때
    private static void setMainKeywordQuery(GetMainSearchReq getMainSearchReq, BoolQueryBuilder boolQueryBuilder){
        if(getMainSearchReq.getKeyword()!=null && !getMainSearchReq.getKeyword().isBlank()){
            boolQueryBuilder.minimumShouldMatch(1);
            setKeyWordByType(getMainSearchReq, boolQueryBuilder);
        }
    }

    // 검색어를 제목이나 본문에서 검색하는 쿼리문 생성 후 boolQueryBuilder에 추가
    private static void setKeyWordByType(GetMainSearchReq getMainSearchReq, BoolQueryBuilder boolQueryBuilder){
        if(getMainSearchReq.getType().contains("tc")){
            // 제목에 대한 검색 조건
            MatchPhraseQueryBuilder titleQueryBuilder = QueryBuilders.matchPhraseQuery("title", getMainSearchReq.getKeyword()).slop(2);
            boolQueryBuilder.should(titleQueryBuilder);
            // 내용에 대한 검색 조건
            MatchQueryBuilder contentQueryBuilder = QueryBuilders.matchQuery("content", getMainSearchReq.getKeyword())
                    .minimumShouldMatch("75%").fuzziness(Fuzziness.AUTO);
            boolQueryBuilder.should(contentQueryBuilder);
        }
    }
    // enable이 true인 위키,에러 아카이브,qan만 검색 결과에 포함되도록
    private static void setAllEnableQuery(BoolQueryBuilder boolQueryBuilder){
        BoolQueryBuilder enableQuery = QueryBuilders.boolQuery()
                .should(QueryBuilders.matchQuery("enable", true))
                .should(QueryBuilders.matchQuery("input_id", "wiki")); // wiki 인덱스는 조건 없이 포함

        boolQueryBuilder.filter(enableQuery);
    }
    private SearchResponse getSearchResponse(GetMainSearchReq getMainSearchReq,BoolQueryBuilder boolQueryBuilder) throws IOException{
        // 검색 요청 객체 생성
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(boolQueryBuilder);
        // 검색 결과 시작 시점
        searchSourceBuilder.from(getMainSearchReq.getPage() * getMainSearchReq.getSize());
        // 검색 결과 페에지 크기 설정
        searchSourceBuilder.size(getMainSearchReq.getSize());
        searchSourceBuilder.sort("created_at", SortOrder.DESC);
        // search()를 통해 검색 수행 후 응답값 받아옴
        SearchRequest searchRequest = new SearchRequest(INDEXS);
        searchRequest.source(searchSourceBuilder);
        return restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
    }
    private GetMainSearchRes makeAllRes(GetMainSearchReq getMainSearchReq, SearchResponse searchResponse) throws JsonProcessingException {
        // 각각의 리스트 생성
        List<GetQnaListRes> qnaListRes = new ArrayList<>();
        List<ListErrorArchiveRes> listErrorArchiveRes = new ArrayList<>();
        List<WikiListRes> wikiListResListRes = new ArrayList<>();

        // SearchHit을 순회하며 각 인덱스에 맞는 리스트에 데이터를 추가
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            if (hit.getIndex().equals("wiki")) {
                // Wiki는 최대 4개까지만 추가
                if (wikiListResListRes.size() >= 4) {
                    continue;
                }
                Wiki wiki = objectMapper.readValue(hit.getSourceAsString(), Wiki.class);
                wikiListResListRes.add(WikiListRes.builder()
                        .id(wiki.getId())
                        .title(wiki.getTitle())
                        .content(wiki.getContent())
                        .category(wiki.getCategoryName())
                        .createdAt(wiki.getCreatedAt())
                        .thumbnail(wiki.getThumbnailImgUrl())
                        .build());
            } else if (hit.getIndex().equals("error_archive")) {
                // Error Archive는 최대 8개까지만 추가
                if (listErrorArchiveRes.size() >= 8) {
                    continue;
                }
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
            } else if (hit.getIndex().equals("qna_board")) {
                // Q&A는 최대 8개까지만 추가
                if (qnaListRes.size() >= 8) {
                    continue;
                }
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

        // GetMainSearchRes 객체 생성 및 리스트 설정
        return GetMainSearchRes.builder()
                .qnaListResList(qnaListRes)
                .errorArchiveResList(listErrorArchiveRes)
                .wikiListResList(wikiListResListRes)
                .build(); // GetMainSearchRes 객체를 반환
    }

}
