package org.example.backend.ErrorArchive.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.backend.Category.Repository.CategoryRepository;
import org.example.backend.Common.BaseResponseStatus;
import org.example.backend.ErrorArchive.Model.Entity.ErrorArchive;
import org.example.backend.ErrorArchive.Model.Entity.ErrorLike;
import org.example.backend.ErrorArchive.Model.Entity.ErrorScrap;
import org.example.backend.ErrorArchive.Model.Req.GetErrorArchiveDetailReq;
import org.example.backend.ErrorArchive.Model.Req.ListErrorArchiveReq;
import org.example.backend.ErrorArchive.Model.Req.RegisterErrorArchiveReq;
import org.example.backend.ErrorArchive.Model.Res.GetErrorArchiveDetailRes;
import org.example.backend.ErrorArchive.Model.Res.ListErrorArchiveRes;
import org.example.backend.ErrorArchive.Model.Res.RegisterErrorArchiveRes;
import org.example.backend.ErrorArchive.Repository.ErrorArchiveReository;
import org.example.backend.ErrorArchive.Repository.ErrorLikeRepository;
import org.example.backend.ErrorArchive.Repository.ErrorScrapRepository;
import org.example.backend.Exception.custom.InvalidCategoryException;
import org.example.backend.Exception.custom.InvalidErrorBoardException;
import org.example.backend.Exception.custom.InvalidUserException;
import org.example.backend.Security.CustomUserDetails;
import org.example.backend.User.Model.Entity.User;
import org.example.backend.User.Repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;


import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



@Service
@RequiredArgsConstructor
public class ErrorArchiveService {

    private final ErrorArchiveReository errorArchiveReository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final ErrorLikeRepository errorLikeRepository;
    private final ErrorScrapRepository errorScrapRepository;

    public RegisterErrorArchiveRes register(RegisterErrorArchiveReq registerErrorArchiveReq, CustomUserDetails customUserDetails) {
        ErrorArchive errorArchive = ErrorArchive.builder()
                .title(registerErrorArchiveReq.getTitle())
                .content(registerErrorArchiveReq.getContent())
                .user(userRepository.findById(customUserDetails.getUserId())
                        .orElseThrow(() -> new InvalidUserException(BaseResponseStatus.USER_NOT_FOUND)))
                .category(categoryRepository.findById(registerErrorArchiveReq.getCategoryId()).orElseThrow(() -> new InvalidCategoryException(BaseResponseStatus.CATEGORY_NOT_FOUND_CATEGORY)))
                .build();
        errorArchiveReository.save(errorArchive);
        return RegisterErrorArchiveRes.builder().errorArchiveId(errorArchive.getId()).build();
    }

    // 아카이브 목록 조회
    public List<ListErrorArchiveRes> errorArchiveList(ListErrorArchiveReq listErrorArchiveReq) {
        Pageable pageable = PageRequest.of(listErrorArchiveReq.getPage(), listErrorArchiveReq.getSize(), Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<ErrorArchive> errorArchivePage = errorArchiveReository.findAll(pageable);

        return errorArchivePage.getContent().stream().map
                        (errorArchive -> ListErrorArchiveRes.builder()
                                .id(errorArchive.getId())
                                .title(errorArchive.getTitle())
                                .content(errorArchive.getContent())
                                .superCategory(errorArchive.getCategory().getSubCategories().toString())
                                .subCategory(errorArchive.getCategory().getSubCategories().toString())
                                .likeCnt(errorArchive.getLikeCount())
                                .build())
                .collect(Collectors.toList());
    }
    // 에러 아카이브 상세 조회
    public GetErrorArchiveDetailRes detail(GetErrorArchiveDetailReq getErrorArchiveDetailReq, CustomUserDetails customUserDetails){
        ErrorArchive errorArchive = errorArchiveReository.findById(getErrorArchiveDetailReq.getId()).orElseThrow(()-> new InvalidErrorBoardException(BaseResponseStatus.ERRORARCHIVE_NOT_FOUND));
        Long userId = (customUserDetails != null) ? customUserDetails.getUserId() : null;
        // 좋아요 상태 조회
        Optional<Boolean> likeStatus = ErrorArchiveLikeOrHate(errorArchive.getId(), userId);
        boolean checkLike = likeStatus.isPresent() && likeStatus.get();
        // 싫어요 상태 조회
        boolean checkHate = likeStatus.isPresent() && !likeStatus.get();
        // 스크랩 여부 조회
        boolean checkScrap = ErrorArchiveScrap(errorArchive.getId(), customUserDetails);

        GetErrorArchiveDetailRes ErrorArchiveDetailRes = GetErrorArchiveDetailRes.builder()
                .id(errorArchive.getId())
                .nickname(errorArchive.getUser().getNickname())
                .title(errorArchive.getTitle())
                .content(errorArchive.getContent())
                .superCategory(errorArchive.getCategory().getCategoryName())
                .subCategory(errorArchive.getCategory().getCategoryName())
                .createAt(errorArchive.getCreatedAt())
                .modifiedAt(errorArchive.getModifiedAt())
                .likeCnt(errorArchive.getLikeCount())
                .hateCnt(errorArchive.getHateCount())
                .checkLike(checkLike)
                .checkHate(checkHate)
                .checkScrap(checkScrap)
                .profileImg(errorArchive.getUser().getProfileImg())
                .grade(errorArchive.getUser().getGrade())
                .gradeImg(errorArchive.getUser().getProfileImg())
                .build();
        return ErrorArchiveDetailRes;
    }
    // 로그인한 사용자의 에러 아카이브 좋아요/싫어요 여부 조회 메소드
    public Optional<Boolean> ErrorArchiveLikeOrHate(Long errorArchiveId,Long userId) {
        if (userId == null) {
            return Optional.empty();
        }
        ErrorArchive errorArchive = errorArchiveReository.findById(errorArchiveId)
                .orElseThrow(() -> new InvalidErrorBoardException(BaseResponseStatus.ERRORARCHIVE_NOT_FOUND));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new InvalidUserException(BaseResponseStatus.USER_NOT_FOUND));
        // 좋아요 또는 싫어요 상태를 확인
        Optional<ErrorLike> repositoryResponseLike = errorLikeRepository.findByUserAndErrorArchiveAndState(user, errorArchive, true);
        Optional<ErrorLike> repositoryResponseHate = errorLikeRepository.findByUserAndErrorArchiveAndState(user, errorArchive, false);
        if (repositoryResponseLike.isPresent()){
            return Optional.of(true);
        }
        if(repositoryResponseHate.isPresent()){
            return Optional.of(false);
        }
        return Optional.empty();
    }


    // 로그인한 사용자의 에러 아카이브 스크랩 여부 조회 메소드
    private boolean ErrorArchiveScrap(Long errorArchiveId, CustomUserDetails customUserDetails){
      Long userId = customUserDetails.getUserId();
      if (userId == null) {
          return false;
      }
    // 에러 아카이브 조회
    ErrorArchive errorArchive = errorArchiveReository.findById(errorArchiveId)
            .orElseThrow(()-> new InvalidErrorBoardException(BaseResponseStatus.ERRORARCHIVE_BEFORE_LIKE));
    // 사용자 조회
    User user = userRepository.findById(userId)
            .orElseThrow(()-> new InvalidUserException(BaseResponseStatus.USER_NOT_FOUND));
    // 스크랩 여부 조회
    Optional<ErrorScrap> errorScrap = errorScrapRepository.findByUserAndErrorArchive(user, errorArchive);
    return errorScrap.isPresent(); //존재하면 스크랩 함, 없으면 스크랩하지 않음
  }
    @Transactional
    public Long toggleErrorArchiveLikeOrHate(Long errorarchiveId, Long userId, boolean isLike) {
        Optional<ErrorArchive> errorArchive = errorArchiveReository.findById(errorarchiveId);
        Optional<User> user = userRepository.findById(userId);

        if (errorArchive.isPresent() && user.isPresent()) {
            Long archiveId = errorArchive.get().getId();
            Optional<ErrorLike> existingLike = errorLikeRepository.findLikeByErrorArchiveIdAndUserIdAndState(archiveId, userId, true);
            Optional<ErrorLike> existingHate = errorLikeRepository.findLikeByErrorArchiveIdAndUserIdAndState(archiveId, userId, false);

            if (isLike) {
                // 좋아요를 누르는 경우
                if (existingLike.isPresent()) {
                    // 이미 좋아요를 누른 경우
                    throw new InvalidErrorBoardException(BaseResponseStatus.ERROR_ALREADY_LIKED);
                }

                if (existingHate.isPresent()) {
                    // 기존에 싫어요가 있는 경우, 싫어요를 삭제
                    errorLikeRepository.delete(existingHate.get());
                    errorArchive.get().decreaseHateCount();
                }

                // 새로운 좋아요 추가
                ErrorLike errorLike = ErrorLike.builder()
                        .errorArchive(errorArchive.get())
                        .user(user.get())
                        .state(true)
                        .build();
                errorLikeRepository.save(errorLike);
                errorArchive.get().increaseLikeCount();
                return errorLike.getId(); // 새 좋아요 ID 반환

            } else {
                // 싫어요를 누르는 경우
                if (existingHate.isPresent()) {
                    // 이미 싫어요를 누른 경우
                    throw new InvalidErrorBoardException(BaseResponseStatus.ERROR_ALREADY_HATED);
                }

                if (existingLike.isPresent()) {
                    // 기존에 좋아요가 있는 경우, 좋아요를 삭제
                    errorLikeRepository.delete(existingLike.get());
                    errorArchive.get().decreaseLikeCount();
                }

                // 새로운 싫어요 추가
                ErrorLike errorLike = ErrorLike.builder()
                        .errorArchive(errorArchive.get())
                        .user(user.get())
                        .state(false)
                        .build();
                errorLikeRepository.save(errorLike);
                errorArchive.get().increaseHateCount();
                return errorLike.getId(); // 새 싫어요 ID 반환
            }
        } else if (errorArchive.isEmpty()) {
            throw new InvalidErrorBoardException(BaseResponseStatus.ERRORARCHIVE_NOT_FOUND);
        } else {
            throw new InvalidUserException(BaseResponseStatus.USER_NOT_FOUND);
        }
    }


    @Transactional
    public Boolean checkErrorArchiveScrap(Long errorarchiveId, Long userId) {
        Optional<ErrorArchive> errorArchive = errorArchiveReository.findById(errorarchiveId);
        Optional<User> user = userRepository.findById(userId);

        if (errorArchive.isPresent() && user.isPresent()) {
            Long archiveId = errorArchive.get().getId();
            Optional<ErrorScrap> existingScrap = errorScrapRepository.findScrapByErrorArchiveIdAndUserId(archiveId, userId);

            if (existingScrap.isPresent()) {
                // 이미 스크랩한 경우, 스크랩을 취소
                errorScrapRepository.delete(existingScrap.get());
                // 스크랩 카운트 감소
                errorArchive.get().decreaseScrapCount();
                return false; // 스크랩이 취소되었음을 나타내는 false 반환
            } else {
                // 새로운 스크랩 추가
                ErrorScrap errorScrap = ErrorScrap.builder()
                        .errorArchive(errorArchive.get())
                        .user(user.get())
                        .build();
                errorScrapRepository.save(errorScrap);
                // 스크랩 카운트 증가
                errorArchive.get().increaseScrapCount();
                return true; // 스크랩이 추가되었음을 나타내는 true 반환
            }
        } else if (errorArchive.isEmpty()) {
            throw new InvalidErrorBoardException(BaseResponseStatus.ERRORARCHIVE_NOT_FOUND_DETAIL);
        } else {
            throw new InvalidUserException(BaseResponseStatus.USER_NOT_FOUND);
        }
    }


}



