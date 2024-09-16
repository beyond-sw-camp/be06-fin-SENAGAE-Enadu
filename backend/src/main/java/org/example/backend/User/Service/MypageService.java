package org.example.backend.User.Service;

import lombok.RequiredArgsConstructor;
import org.example.backend.Common.BaseResponseStatus;
import org.example.backend.Exception.custom.InvalidUserException;
import org.example.backend.Qna.Repository.QuestionRepository;
import org.example.backend.Qna.model.Entity.QnaBoard;
import org.example.backend.User.Model.Entity.User;
import org.example.backend.User.Model.Res.GetUserInfoRes;
import org.example.backend.User.Model.Res.GetUserQnaListRes;
import org.example.backend.User.Repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MypageService {
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;

    public GetUserInfoRes getUserInfo(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            return GetUserInfoRes.builder()
                    .email(user.getEmail())
                    .nickname(user.getNickname())
                    .isSocialUser(!"InApp".equals(user.getType()))
                    .profileImg(user.getProfileImg())
                    .grade(user.getGrade())
                    .build();
        }
        throw new InvalidUserException(BaseResponseStatus.USER_NOT_FOUND);
    }

    public List<GetUserQnaListRes> getUserQnaList(Long id, Integer page, Integer size) {
        userRepository.findById(id).orElseThrow(() -> new InvalidUserException(BaseResponseStatus.USER_NOT_FOUND));
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<QnaBoard> qnaBoardPage = questionRepository.findByUserId(id, pageable);
        if (qnaBoardPage.isEmpty()) {
            return null;
        }
        List<GetUserQnaListRes> getUserQnaList = new ArrayList<>();
        for (QnaBoard qnaBoard : qnaBoardPage) {
            GetUserQnaListRes getUserQnaListRes = GetUserQnaListRes.builder()
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
                    .build();
            getUserQnaList.add(getUserQnaListRes);
        }
        return getUserQnaList;
    }
}
