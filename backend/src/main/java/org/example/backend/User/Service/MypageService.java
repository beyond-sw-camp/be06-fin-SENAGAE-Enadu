package org.example.backend.User.Service;

import lombok.RequiredArgsConstructor;
import org.example.backend.Common.BaseResponseStatus;
import org.example.backend.Exception.custom.InvalidUserException;
import org.example.backend.Qna.Repository.QuestionRepository;
import org.example.backend.Qna.model.Entity.QnaBoard;
import org.example.backend.User.Model.Entity.User;
import org.example.backend.User.Model.Res.GetUserInfoRes;
import org.example.backend.User.Model.Res.GetUserQnaListRes;
import org.example.backend.User.Model.Res.GetUserWikiListRes;
import org.example.backend.User.Repository.UserRepository;
import org.example.backend.Wiki.Model.Entity.WikiContent;
import org.example.backend.Wiki.Repository.WikiContentRepository;
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
    private final WikiContentRepository contentRepository;

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

    public List<GetUserQnaListRes> getUserQnaList(Long id, Integer page, Integer size, String type) {
        userRepository.findById(id).orElseThrow(() -> new InvalidUserException(BaseResponseStatus.USER_NOT_FOUND));
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<QnaBoard> qnaBoardPage = null;
        if (type.equals("question")) {
            qnaBoardPage = questionRepository.findByUserId(id, pageable);
        } else if (type.equals("answer")) {
            qnaBoardPage = questionRepository.findByUser_AnswerList_User_Id(id, pageable);
        } else if (type.equals("scrap")) {
            qnaBoardPage = questionRepository.findByQnaScrapList_User_Id(id, pageable);
        }
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
                    .totalPage(qnaBoardPage.getTotalPages())
                    .build();
            getUserQnaList.add(getUserQnaListRes);
        }
        return getUserQnaList;
    }

    public List<GetUserWikiListRes> getUserWikiList(Long id, Integer page, Integer size, String type) {
        userRepository.findById(id).orElseThrow(() -> new InvalidUserException(BaseResponseStatus.USER_NOT_FOUND));
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<WikiContent> contentsPage = null;
        if (type.equals("log")) {
            contentsPage = contentRepository.findByUser_Id(id, pageable);
        } else if (type.equals("scrap")) {
            contentsPage = contentRepository.findByWikiScrapList_User_Id(id, pageable);
        }
        if (contentsPage.isEmpty()) {
            return null;
        }
        List<GetUserWikiListRes> getUserWikiList = new ArrayList<>();
        for (WikiContent wikiContent : contentsPage) {
            GetUserWikiListRes getUserWikiListRes = GetUserWikiListRes.builder()
                    .id(wikiContent.getId())
                    .title(wikiContent.getWiki().getTitle())
                    .content(wikiContent.getContent())
                    .category(wikiContent.getWiki().getCategory().getCategoryName())
                    .thumbnail(wikiContent.getThumbnail())
                    .createdAt(wikiContent.getCreatedAt())
                    .totalPages(contentsPage.getTotalPages())
                    .build();
            getUserWikiList.add(getUserWikiListRes);
        }
        return getUserWikiList;
    }
}
