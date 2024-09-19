package org.example.backend.ErrorArchive.Service;

import lombok.RequiredArgsConstructor;
import org.example.backend.Category.Repository.CategoryRepository;
import org.example.backend.Common.BaseResponseStatus;
import org.example.backend.ErrorArchive.Model.Entity.ErrorArchive;
import org.example.backend.ErrorArchive.Model.Req.ListErrorArchiveReq;
import org.example.backend.ErrorArchive.Model.Req.RegisterErrorArchiveReq;
import org.example.backend.ErrorArchive.Model.Res.ListErrorArchiveRes;
import org.example.backend.ErrorArchive.Model.Res.RegisterErrorArchiveRes;
import org.example.backend.ErrorArchive.Repository.ErrorArchiveReository;
import org.example.backend.Exception.custom.InvalidCategoryException;
import org.example.backend.Exception.custom.InvalidErrorBoardException;
import org.example.backend.Exception.custom.InvalidUserException;
import org.example.backend.Security.CustomUserDetails;
import org.example.backend.User.Repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;



@Service
@RequiredArgsConstructor
public class ErrorArchiveService {

    private final ErrorArchiveReository errorArchiveReository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
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
                                .superCategory(errorArchive.getCategory().getCategoryName())
                                .subCategory(errorArchive.getCategory().getCategoryName())
                                .likeCnt(errorArchive.getLikeCount())
                                .createAt(String.valueOf(errorArchive.getCreatedAt()))
                                .grade(errorArchive.getUser().getGrade())
                                .nickname(errorArchive.getUser().getNickname())
                                .profileImg(errorArchive.getUser().getProfileImg())
                                .totalPage(errorArchivePage.getTotalPages())
                                .build())
                .collect(Collectors.toList());
    }
}



