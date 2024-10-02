package org.example.backend.Qna.Service;

import lombok.RequiredArgsConstructor;
import org.example.backend.Common.BaseResponseStatus;
import org.example.backend.Exception.custom.InvalidQnaException;
import org.example.backend.Qna.Repository.QuestionRepository;
import org.example.backend.Qna.model.Entity.QnaBoard;
import org.example.backend.Qna.model.Res.GetQnaListRes;
import org.example.backend.Qna.model.req.GetQnaSearchReq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class BasicQnaSearchService implements QnaSearchService {
    private final QuestionRepository questionRepository;

    @Override
    public List<GetQnaListRes> getQnaSearch(GetQnaSearchReq getQnaSearchReq) {
        boolean notChosenCategory = false;
        if (getQnaSearchReq.getCategoryId() == null || getQnaSearchReq.getCategoryId() < 1) {
            notChosenCategory = true;
        }

        Pageable pageable;
        // 최신순 or 좋아요가 많은 순으로 정렬 type 지정, 둘 다 아니면 에러
        if (getQnaSearchReq.getSort().equals("latest")) {
            pageable = PageRequest.of(getQnaSearchReq.getPage(), getQnaSearchReq.getSize(), Sort.by(Sort.Direction.DESC, "createdAt"));
        } else if (getQnaSearchReq.getSort().equals("like")) {
            pageable = PageRequest.of(getQnaSearchReq.getPage(), getQnaSearchReq.getSize(), Sort.by(Sort.Direction.DESC, "likeCount"));
        } else {
            throw new InvalidQnaException(BaseResponseStatus.QNA_INVALID_SEARCH_TYPE);
        }
        // paging 처리 및 responseList 생성
        Page<QnaBoard> qnaBoardPage;

        if (getQnaSearchReq.getType().equals("tc")) {
            qnaBoardPage = questionRepository.findByTC(getQnaSearchReq.getKeyword(), getQnaSearchReq.getCategoryId(), notChosenCategory, pageable);
        } else if (getQnaSearchReq.getType().equals("t")) {
            qnaBoardPage = questionRepository.findByT(getQnaSearchReq.getKeyword(), getQnaSearchReq.getCategoryId(), notChosenCategory, pageable);
        } else if (getQnaSearchReq.getType().equals("c")) {
            qnaBoardPage = questionRepository.findByC(getQnaSearchReq.getKeyword(), getQnaSearchReq.getCategoryId(), notChosenCategory, pageable);
        } else {
            throw new InvalidQnaException(BaseResponseStatus.QNA_INVALID_SEARCH_TYPE);
        }
        return qnaBoardPage.getContent().stream().map
                        (qnaBoard -> GetQnaListRes.builder()
                                .id(qnaBoard.getId())
                                .title(qnaBoard.getTitle())
                                .superCategoryName(qnaBoard.getCategory().getSuperCategory() != null ?
                                        qnaBoard.getCategory().getSuperCategory().getCategoryName() : null)
                                .subCategoryName(qnaBoard.getCategory() != null ?
                                        qnaBoard.getCategory().getCategoryName() : null)
                                .subCategoryName(qnaBoard.getCategory().getCategoryName())
                                .nickname(qnaBoard.getUser().getNickname())
                                .profileImage(qnaBoard.getUser().getProfileImg())
                                .grade(qnaBoard.getUser().getGrade())
                                .likeCnt(qnaBoard.getLikeCount())
                                .answerCnt(qnaBoard.getAnswerCount())
                                .createdAt(qnaBoard.getCreatedAt())
                                .build())
                .collect(Collectors.toList());
    }
}
