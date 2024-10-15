package org.example.backend.Qna.Service;

import com.example.common.Category.Model.Entity.Category;
import com.example.common.Category.Repository.CategoryRepository;
import com.example.common.Qna.Repository.QnaRepositoryCustomImpl;
import com.example.common.Qna.model.Entity.QnaBoard;
import lombok.RequiredArgsConstructor;
import org.example.backend.Common.BaseResponseStatus;
import org.example.backend.Exception.custom.InvalidQnaException;
import org.example.backend.Qna.model.Res.GetQnaListRes;
import org.example.backend.Qna.model.req.GetQnaSearchReq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service("QnaDbSearch")
@RequiredArgsConstructor
public class BasicQnaSearchService implements QnaSearchService {
    private final CategoryRepository categoryRepository;
    private final QnaRepositoryCustomImpl qnaRepositoryCustom;

    @Override
    public List<GetQnaListRes> getQnaSearch(GetQnaSearchReq getQnaSearchReq) {
        Optional<Category> category;
        boolean useSuperCategory = false;
        if (getQnaSearchReq.getCategoryId() != null) {
            category = categoryRepository.findById(getQnaSearchReq.getCategoryId());
            if (category.get().getSuperCategory() == null) {
                useSuperCategory = true;
            }
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

        if (getQnaSearchReq.getType().equals("tc") || getQnaSearchReq.getType().equals("t") || getQnaSearchReq.getType().equals("c")) {
            qnaBoardPage = qnaRepositoryCustom.findByKeyword(getQnaSearchReq.getKeyword(), useSuperCategory, getQnaSearchReq.getCategoryId(), getQnaSearchReq.getType(), getQnaSearchReq.getResolved(), pageable);

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
                                .totalPage(qnaBoardPage.getTotalPages())
                                .build())
                .collect(Collectors.toList());
    }
}
