package org.example.backend.User.Service;

import lombok.RequiredArgsConstructor;
import org.example.backend.Common.BaseResponseStatus;
import com.example.common.ErrorArchive.Model.Entity.ErrorArchive;
import com.example.common.ErrorArchive.Repository.ErrorArchiveReository;
import org.example.backend.Exception.custom.InvalidUserException;
import com.example.common.Qna.Repository.QuestionRepository;
import com.example.common.Qna.model.Entity.QnaBoard;
import com.example.common.User.Model.Entity.User;
import org.example.backend.User.Model.Res.*;
import com.example.common.User.Repository.UserRepository;
import com.example.common.Wiki.Model.Entity.WikiContent;
import com.example.common.Wiki.Repository.WikiContentRepository;
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
    private final ErrorArchiveReository errorArchiveReository;

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

    public GetLogUserInfoRes getLogUserInfo(String nickname) {
        User user = userRepository.findByNickname(nickname).orElseThrow(() -> new InvalidUserException(BaseResponseStatus.USER_NOT_FOUND));
        if (!user.getEnable()) {
            throw new InvalidUserException(BaseResponseStatus.USER_INACTIVE_ACCOUNT);
        }
        return GetLogUserInfoRes.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .profileImg(user.getProfileImg())
                .grade(user.getGrade())
                .build();
    }

    public List<GetUserQnaListRes> getUserQnaList(Long id, Integer page, Integer size, String type) {
        userRepository.findByIdAndEnableTrue(id).orElseThrow(() -> new InvalidUserException(BaseResponseStatus.USER_NOT_FOUND));
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<QnaBoard> qnaBoardPage = switch (type) {
            case "question" -> questionRepository.findByUserIdAndEnableTrueWithFetch(id, pageable); // 1 + n => 1개

            case "answer" ->
                    questionRepository.findByUserAnswerListUserIdAndEnableTrueWithFetch(id, pageable); // 1 + n => 1개

            case "scrap" -> questionRepository.findByQnaScrapListUserIdAndEnableTrueWithFetch(id, pageable); // 2 + n => 1개
            default -> null;

        };
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
        userRepository.findByIdAndEnableTrue(id).orElseThrow(() -> new InvalidUserException(BaseResponseStatus.USER_NOT_FOUND));
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<WikiContent> contentsPage = null;
        if (type.equals("log")) {
            contentsPage = contentRepository.findByUserIdWithFetch(id, pageable); // 3 => 2개
        } else if (type.equals("scrap")) {
            contentsPage = contentRepository.findByWikiScrapListUserIdWithWikiAndCategory(id, pageable); // 2 + n => 1개
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
                    .totalPage(contentsPage.getTotalPages())
                    .build();
            getUserWikiList.add(getUserWikiListRes);
        }
        return getUserWikiList;
    }

    public List<GetUserArchiveListRes> getUserArchiveList(Long id, Integer page, Integer size, String type) {
        userRepository.findByIdAndEnableTrue(id).orElseThrow(() -> new InvalidUserException(BaseResponseStatus.USER_NOT_FOUND));
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<ErrorArchive> contentsPage = null;
        if (type.equals("log")) {
            contentsPage = errorArchiveReository.findByUserIdAndEnableTrueWithFetch(id, pageable); // 3 => 2개
        } else if (type.equals("scrap")) {
            contentsPage = errorArchiveReository.findByErrorScrapListUserIdAndEnableTrueWithFetch(id, pageable); // 2 + n => 1개
        }
        if (contentsPage.isEmpty()) {
            return null;
        }
        List<GetUserArchiveListRes> getUserArchiveList = new ArrayList<>();
        for (ErrorArchive errorArchive : contentsPage) {
            GetUserArchiveListRes getUserArchiveListRes = GetUserArchiveListRes.builder()
                    .id(errorArchive.getId())
                    .title(errorArchive.getTitle())
                    .content(errorArchive.getContent())
                    .superCategory(errorArchive.getCategory().getSuperCategory() == null ?
                            errorArchive.getCategory().getCategoryName() :
                            errorArchive.getCategory().getSuperCategory().getCategoryName())
                    .subCategory(errorArchive.getCategory().getSuperCategory() == null ? null: errorArchive.getCategory().getCategoryName())
                    .likeCnt(errorArchive.getLikeCount())
                    .createdAt(String.valueOf(errorArchive.getCreatedAt()))
                    .grade(errorArchive.getUser().getGrade())
                    .nickname(errorArchive.getUser().getNickname())
                    .profileImg(errorArchive.getUser().getProfileImg())
                    .totalPage(contentsPage.getTotalPages())
                    .build();
            getUserArchiveList.add(getUserArchiveListRes);
        }
        return getUserArchiveList;
    }
}
