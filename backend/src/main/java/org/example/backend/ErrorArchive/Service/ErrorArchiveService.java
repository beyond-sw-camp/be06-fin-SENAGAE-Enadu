package org.example.backend.ErrorArchive.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.backend.Category.Model.Entity.Category;
import org.example.backend.Category.Repository.CategoryRepository;
import org.example.backend.Common.BaseResponse;
import org.example.backend.Common.BaseResponseStatus;
import org.example.backend.ErrorArchive.Model.Entity.ErrorArchive;
import org.example.backend.ErrorArchive.Model.Entity.ErrorLike;
import org.example.backend.ErrorArchive.Model.Entity.ErrorScrap;
import org.example.backend.ErrorArchive.Model.Req.*;
import org.example.backend.ErrorArchive.Model.Res.*;
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
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
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
        Pageable pageable;
        if(listErrorArchiveReq.getSort() == null){
            listErrorArchiveReq.setSort("latest");
        }
        if(listErrorArchiveReq.getSort().equals("latest")) {
            pageable = PageRequest.of(listErrorArchiveReq.getPage(), listErrorArchiveReq.getSize(), Sort.by(Sort.Direction.DESC, "createdAt"));
        } else if(listErrorArchiveReq.getSort().equals("like")) {
            pageable = PageRequest.of(listErrorArchiveReq.getPage(),listErrorArchiveReq.getSize(), Sort.by(Sort.Direction.DESC, "likeCount"));
        } else {
            throw new InvalidErrorBoardException(BaseResponseStatus.ERRORARCHIVE_INVALID_SEARCH_TYPE);
        }
        Page<ErrorArchive> errorArchivePage = errorArchiveReository.findAll(pageable);
        return errorArchivePage.getContent().stream().map
                        (errorArchive -> ListErrorArchiveRes.builder()
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
                                .totalPage(errorArchivePage.getTotalPages())
                                .build())
                .collect(Collectors.toList());
    }
    // 에러 아카이브 상세 조회
    public GetErrorArchiveDetailRes detail(GetErrorArchiveDetailReq getErrorArchiveDetailReq, CustomUserDetails customUserDetails){
        ErrorArchive errorArchive = errorArchiveReository.findById(getErrorArchiveDetailReq.getId()).orElseThrow(()-> new InvalidErrorBoardException(BaseResponseStatus.ERRORARCHIVE_NOT_FOUND));
        Boolean checkLike = null;
        Boolean checkHate =  null;
        Boolean checkScrap = null;
        if (customUserDetails != null) {
            Long userId = customUserDetails.getUserId();
            // 좋아요 상태 조회
            Optional<Boolean> likeStatus = ErrorArchiveLikeOrHate(errorArchive.getId(), userId);
            checkLike = likeStatus.isPresent() && likeStatus.get();
            // 싫어요 상태 조회
            checkHate = likeStatus.isPresent() && !likeStatus.get();
            // 스크랩 여부 조회
            checkScrap = ErrorArchiveScrap(errorArchive.getId(), customUserDetails);
        }


        GetErrorArchiveDetailRes ErrorArchiveDetailRes = GetErrorArchiveDetailRes.builder()
                .id(errorArchive.getId())
                .authorId(errorArchive.getUser().getId()) // 작성자 ID
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
    public LikeOrHateRes toggleErrorArchiveLikeOrHate(Long errorarchiveId, Long userId, boolean isLike) {
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
                return LikeOrHateRes.builder().result(true).build(); // true 반환
            } else if (errorLike.isState()) {
                // 이미 좋아요를 누른 상태에서 다시 좋아요를 누른 경우(좋아요 취소)
                errorLikeRepository.delete(errorLike);
                errorArchive.setLikeCnt(errorArchive.getLikeCount() - 1);
                errorArchiveReository.save(errorArchive);
                return LikeOrHateRes.builder().result(null).build(); // null 반환
            } else {
                errorLike.updateState(true);
                // 이미 싫어요를 누른 상태에서 좋아요로 변경하는 경우
                errorLikeRepository.save(errorLike);
                errorArchive.setLikeCnt(errorArchive.getLikeCount() + 1);
                errorArchive.setHateCnt(errorArchive.getHateCount() - 1);
                errorArchiveReository.save(errorArchive);
                return LikeOrHateRes.builder().result(true).build(); // true 반환
            }
        }
        // 싫어요 관련 로직
        else {
            if (errorLike == null) {
                // 아무것도 누르지 않는 상태에서 싫어요를 누른 경우
                ErrorLike newHate = ErrorLike.builder()
                        .errorArchive(errorArchive)
                        .user(user)
                        .state(false)
                        .build();
                errorLikeRepository.save(newHate);
                errorArchive.setHateCnt(errorArchive.getHateCount() + 1);
                errorArchiveReository.save(errorArchive);
                return LikeOrHateRes.builder().result(false).build(); // false 반환
            } else if (!errorLike.isState()) {
                // 이미 싫어요를 누른 상태에서 다시 싫어요를 누른 경우 (싫어요 취소)
                errorLikeRepository.delete(errorLike);
                errorArchive.setHateCnt(errorArchive.getHateCount() - 1);
                errorArchiveReository.save(errorArchive);
                return LikeOrHateRes.builder().result(null).build(); // null 반환
            } else {
                // 이미 좋아요를 누른 상태에서 싫어요로 변경하는 경우
                errorLike.updateState(false);
                errorLikeRepository.save(errorLike);
                errorArchive.setHateCnt(errorArchive.getHateCount() + 1);
                errorArchive.setLikeCnt(errorArchive.getLikeCount() - 1);
                errorArchiveReository.save(errorArchive);
                return LikeOrHateRes.builder().result(false).build(); // false 반환
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
    // 에러 아카이브 수정
    @Transactional
    public GetErrorArchiveUpdateRes update(GetErrorArchiveUpdateReq getErrorArchiveUpdateReq, Long userId){
        // 사용자 조회
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new InvalidUserException(BaseResponseStatus.USER_NOT_FOUND));
        // 기존 에러 아카이브 조회
        ErrorArchive errorArchive = errorArchiveReository.findById(getErrorArchiveUpdateReq.getId()).orElseThrow(()-> new InvalidErrorBoardException(BaseResponseStatus.ERROR_PERMISSION_DENIED));
        // 작성자 확인
        if(!errorArchive.getUser().getId().equals(userId)){
            throw new InvalidErrorBoardException(BaseResponseStatus.ERROR_PERMISSION_DENIED);
        }

        // 카테고리 업데이트
        Category updatedCategory = null;
        if(getErrorArchiveUpdateReq.getCategoryId() != null){
            updatedCategory = categoryRepository.findById(getErrorArchiveUpdateReq.getCategoryId())
                    .orElseThrow(()-> new InvalidErrorBoardException(BaseResponseStatus.CATEGORY_NOT_FOUND_CATEGORY));
        } else {
            updatedCategory = errorArchive.getCategory();
        }
        // 에러 아카이브 수정
        errorArchive.updateTitle(getErrorArchiveUpdateReq.getTitle());
        errorArchive.updateContent(getErrorArchiveUpdateReq.getContent());
        errorArchive.updateCategory(updatedCategory);
        // 변경된 엔티티 저장
        errorArchiveReository.save(errorArchive);
        return GetErrorArchiveUpdateRes.builder()
                .id(errorArchive.getId()) // 업데이트된 에러 아카이브의 ID 반환
                .build();
    }
    @Transactional
    public Boolean delete(RemoveErrorArchiveReq removeErrorArchiveReq, Long userId) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new InvalidUserException(BaseResponseStatus.USER_NOT_FOUND));
            ErrorArchive errorArchive = errorArchiveReository.findById(removeErrorArchiveReq.getId())
                    .orElseThrow(() -> new InvalidErrorBoardException(BaseResponseStatus.ERROR_PERMISSION_DENIED));

            // 작성자 확인
            if (!errorArchive.getUser().getId().equals(userId)) {
                throw new InvalidErrorBoardException(BaseResponseStatus.ERROR_PERMISSION_DENIED);
            }

            errorArchive.updateEnalbe(false);
            errorArchiveReository.save(errorArchive);
            return true; // 정상적으로 삭제되면 true 반환
        } catch (Exception e) {
            // 예외가 발생한 경우 false 반환
            return false;
        }
    }

}



