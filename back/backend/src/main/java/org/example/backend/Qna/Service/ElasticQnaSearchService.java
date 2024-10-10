package org.example.backend.Qna.Service;

import lombok.RequiredArgsConstructor;
import org.example.backend.Qna.Repository.ElasticsearchQuestionRepository;
import org.example.backend.Qna.model.Doc.QnaBoard;
import org.example.backend.Qna.model.Res.GetQnaListRes;
import org.example.backend.Qna.model.req.GetQnaSearchReq;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ElasticQnaSearchService implements QnaSearchService {

    private final ElasticsearchQuestionRepository elasticsearchQuestionRepository;

    @Override
    public List<GetQnaListRes> getQnaSearch(GetQnaSearchReq getQnaSearchReq) { // 테스트 용도
        Iterable<QnaBoard> all = elasticsearchQuestionRepository.findAll();
        List<GetQnaListRes> getQnaListRes = new ArrayList<>();
        for (QnaBoard qnaBoard : all) {
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
                    .build());
        }
        return getQnaListRes;
    }
}
