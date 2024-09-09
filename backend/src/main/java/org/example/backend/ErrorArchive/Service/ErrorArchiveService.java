package org.example.backend.ErrorArchive.Service;

import lombok.AllArgsConstructor;
import org.example.backend.Category.Model.Entity.Category;
import org.example.backend.Category.Repository.CategoryRepository;
import org.example.backend.Common.BaseResponseStatus;
import org.example.backend.ErrorArchive.Model.Entity.ErrorArchive;
import org.example.backend.ErrorArchive.Model.Req.RegisterErrorArchiveReq;
import org.example.backend.ErrorArchive.Repository.ErrorArchiveReository;
import org.example.backend.Exception.custom.InvalidUserException;
import org.example.backend.Security.CustomUserDetails;
import org.example.backend.User.Model.Entity.User;
import org.example.backend.User.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestPart;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ErrorArchiveService {
    @Autowired
    private final ErrorArchiveReository errorArchiveReository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;


    // 에러 아카이브 등록
    // ToDo: customUserDetails에 유저 정보 추가

    public void register(RegisterErrorArchiveReq registerErrorArchiveReq, CustomUserDetails customUserDetails) {
        // request를 엔티티로 만드는 과정
        Optional<Category> category = categoryRepository.findById(registerErrorArchiveReq.getCategoryId());
        Optional<User> user = userRepository.findByEmail(customUserDetails.getUsername());
        if(category.isPresent() && user.isPresent()){
            ErrorArchive errorArchive = ErrorArchive.builder()
                    .title(registerErrorArchiveReq.getTitle())
                    .content(registerErrorArchiveReq.getContent())
                    .category(category.get())
                    .user(user.get())
                    .build();
            errorArchive.createdAt();
            errorArchive.modifiedAt();
            errorArchiveReository.save(errorArchive);
        } else if (category.isEmpty()){
            throw new InvalidUserException(BaseResponseStatus.INVALID_CATEGORY_DATA);
        } else {
            throw new InvalidUserException(BaseResponseStatus.USER_NOT_FOUND);
        }
    }
}



