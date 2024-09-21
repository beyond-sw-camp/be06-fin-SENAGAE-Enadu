package org.example.backend.ErrorArchive.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.backend.Category.Repository.CategoryRepository;
import org.example.backend.Common.BaseResponse;
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
import org.example.backend.ErrorArchive.Model.Res.ToggleResponse;
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

    // 에러 아카이브 조회
    ErrorArchive errorArchive = errorArchiveReository.findById(errorArchiveId)
            .orElseThrow(()-> new InvalidErrorBoardException(BaseResponseStatus.ERRORARCHIVE_BEFORE_LIKE));
    // 사용자 조회
    User user = userRepository.findById(userId)
            .orElseThrow(()-> new InvalidUserException(BaseResponseStatus.USER_NOT_FOUND));
    // 스크랩 여부 조회
    Optional<ErrorScrap> errorScrap = errorScrapRepository.findByUserAndErrorArchive(user,errorArchive);
    return errorScrap.isPresent(); //존재하면 스크랩 함, 없으면 스크랩하지 않음
  }
    @Transactional
    public BaseResponse<ToggleResponse> toggleErrorArchiveLikeOrHate(Long errorarchiveId, Long userId, boolean isLike) {
        ErrorArchive errorArchive = errorArchiveReository.findById(errorarchiveId).orElseThrow(() -> new InvalidErrorBoardException(BaseResponseStatus.ERRORARCHIVE_NOT_FOUND));
        User user = userRepository.findById(userId).orElseThrow(() -> new InvalidUserException(BaseResponseStatus.USER_NOT_FOUND));
        // 사용자의 좋아요, 싫어요 상태 가져오기
        ErrorLike errorLike = errorLikeRepository.findByErrorArchiveAndUser(errorArchive, user).orElse(null);
        // 좋아요 관련 로직
        if (isLike) {
            if (errorLike == null) {
                // 사용자가 처음 좋아요를 누른 경우
                ErrorLike newLike =  ErrorLike.builder()
                        .errorArchive(errorArchive)
                        .user(user)
                        .state(true)
                        .build();
                errorLikeRepository.save(newLike);
                errorArchive.setLikeCnt(errorArchive.getLikeCount() + 1);
                errorArchiveReository.save(errorArchive);
                return new BaseResponse<>(new ToggleResponse(true)); // true 반환
            } else if (errorLike.isState()) {
                // 이미 좋아요를 누른 상태에서 다시 좋아요를 누른 경우(좋아요 취소)
                errorLikeRepository.delete(errorLike);
                errorArchive.setLikeCnt(errorArchive.getLikeCount() - 1);
                errorArchiveReository.save(errorArchive);
                return new BaseResponse<>(new ToggleResponse(null)); // null 반환
            } else {
                errorLike.updateState(true);
                // 이미 싫어요를 누른 상태에서 좋아요로 변경하는 경우
                errorLikeRepository.save(errorLike);
                errorArchive.setLikeCnt(errorArchive.getLikeCount() + 1);
                errorArchive.setHateCnt(errorArchive.getHateCount() - 1);
                errorArchiveReository.save(errorArchive);
                return new BaseResponse<>(new ToggleResponse(true)); // true 반환
            }
        }
        // 싫어요 관련 로직
        else {
            if (errorLike == null) {
                // 아무것도 누르지 않는 상태에서 싫어요를 누른 경우
                ErrorLike newHate = ErrorLike.builder()
                        .errorArchive(errorArchive)
                        .user(user)
                        .state(true)
                        .build();
                errorLikeRepository.save(newHate);
                errorArchive.setHateCnt(errorArchive.getHateCount() + 1);
                errorArchiveReository.save(errorArchive);
                return new BaseResponse<>(new ToggleResponse(false)); // false 반환
            } else if (!errorLike.isState()) {
                // 이미 싫어요를 누른 상태에서 다시 싫어요를 누른 경우 (싫어요 취소)
                errorLikeRepository.delete(errorLike);
                errorArchive.setHateCnt(errorArchive.getHateCount() - 1);
                errorArchiveReository.save(errorArchive);
                return new BaseResponse<>(new ToggleResponse(null)); // null 반환
            } else {
                // 이미 좋아요를 누른 상태에서 싫어요로 변경하는 경우
                errorLike.updateState(false);
                errorLikeRepository.save(errorLike);
                errorArchive.setHateCnt(errorArchive.getHateCount() + 1);
                errorArchive.setLikeCnt(errorArchive.getLikeCount() - 1);
                errorArchiveReository.save(errorArchive);
                return new BaseResponse<>(new ToggleResponse(false)); // false 반환
            }
        }
        // 좋아요가 되면 TRUE, 싫어요가 되면 FALSE, 취소되면 null 로 반환
    }

    @Transactional
    public Boolean checkErrorArchiveScrap(Long errorarchiveId, Long userId) {
        ErrorArchive errorArchive = errorArchiveReository.findById(errorarchiveId).orElseThrow(() -> new InvalidErrorBoardException(BaseResponseStatus.ERRORARCHIVE_NOT_FOUND));
        User user = userRepository.findById(userId).orElseThrow(() -> new InvalidUserException(BaseResponseStatus.USER_NOT_FOUND));
        // 사용자가 해당 아카이브를 스크랩했는지 여부 확인
        ErrorScrap errorScrap = errorScrapRepository.findByUserAndErrorArchive(user, errorArchive).orElse(null);
        // 스크랩 로직
        if (errorScrap == null){
            // 사용자가 처음 스크랩을 한 경우
            // 빌더로
            ErrorScrap newScrap = new ErrorScrap();
            newScrap.setErrorArchive(errorArchive);
            // 메서드 추가
            newScrap.setUser(user);
            errorScrapRepository.save(newScrap);
            // 스크랩 카운트 증가
            errorArchive.setScrapCount(errorArchive.getScrapCount()+1);
            errorArchiveReository.save(errorArchive);
            return true;
        } else {
            // 이미 스크랩을 한 경우, 스크랩을 취소함
            errorScrapRepository.delete(errorScrap);
            errorArchive.setScrapCount(errorArchive.getScrapCount()-1);
            errorArchiveReository.save(errorArchive);
            return false;
        }
    }
}



