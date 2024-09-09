package org.example.backend.Qna.Service;

import lombok.RequiredArgsConstructor;
import org.example.backend.Category.Model.Entity.Category;
import org.example.backend.Category.Repository.CategoryRepository;
import org.example.backend.Common.BaseResponseStatus;
import org.example.backend.Exception.custom.InvalidQnaException;
import org.example.backend.Exception.custom.InvalidUserException;
import org.example.backend.Qna.Repository.QuestionRepository;
import org.example.backend.Qna.model.Entity.QnaBoard;
import org.example.backend.Qna.model.Entity.Res.GetQnaListRes;
import org.example.backend.Qna.model.Entity.req.CreateQuestionReq;
import org.example.backend.Qna.model.Entity.req.GetQnaListReq;
import org.example.backend.Security.CustomUserDetails;
import org.example.backend.User.Model.Entity.User;
import org.example.backend.User.Repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QnaService {
    private final QuestionRepository questionRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public void saveQuestion(CreateQuestionReq createQuestionReq, CustomUserDetails customUserDetails) {
        Optional<Category> category = categoryRepository.findById(createQuestionReq.getCategoryId());
        Optional<User> user = userRepository.findById(customUserDetails.getUserId());

        if (category.isPresent() && user.isPresent()) {
            QnaBoard qnaBoard = QnaBoard.builder()
                    .user(user.get())
                    .title(createQuestionReq.getTitle())
                    .content(createQuestionReq.getContent())
                    .category(category.get())
                    .build();

            qnaBoard.createdAt();
            questionRepository.save(qnaBoard);
        }
        else if (category.isEmpty()) {
            throw new InvalidQnaException(BaseResponseStatus.INVALID_CATEGORY_DATA);
        } else {
            throw new InvalidUserException(BaseResponseStatus.USER_NOT_FOUND);
        }
    }

    public List<GetQnaListRes> getQnaList(GetQnaListReq getQnaListReq) {
        Pageable pageable;
        // 최신순 or 좋아요가 많은 순으로 정렬 type 지정, 둘 다 아니면 에러
        if (getQnaListReq.getSort().equals("latest")) {
            pageable = PageRequest.of(getQnaListReq.getPage(), getQnaListReq.getSize(), Sort.by(Sort.Direction.DESC, "createdAt"));
        }
        else if(getQnaListReq.getSort().equals("like")) {
            pageable = PageRequest.of(getQnaListReq.getPage(), getQnaListReq.getSize(), Sort.by(Sort.Direction.DESC, "likeCnt"));
        }
        else {
            throw new InvalidQnaException(BaseResponseStatus.INVALID_SEARCH_TYPE);
        }
        // paging 처리 및 responseList 생성
        Page<QnaBoard> qnaBoardPage = questionRepository.findAll(pageable);

        return qnaBoardPage.getContent().stream().map
                        (qnaBoard -> GetQnaListRes.builder()
                        .id(qnaBoard.getId())
                        .title(qnaBoard.getTitle())
                        .superCategory(qnaBoard.getCategory() != null &&
                                        qnaBoard.getCategory().getSuperCategory() != null ?
                                        qnaBoard.getCategory().getSuperCategory().getCategoryName() : null)
                        .subCategory(qnaBoard.getCategory().getCategoryName())
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
