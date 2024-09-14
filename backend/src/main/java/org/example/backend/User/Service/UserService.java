package org.example.backend.User.Service;

import lombok.RequiredArgsConstructor;
import org.example.backend.Common.BaseResponseStatus;
import org.example.backend.Exception.custom.InvalidUserException;
import org.example.backend.User.Model.Entity.User;
import org.example.backend.User.Model.Req.UserSignupReq;
import org.example.backend.User.Repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public void signup(UserSignupReq userSignupReq, String profileImg ) {
        User user = User.builder()
                .email(userSignupReq.getEmail())
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .profileImg(profileImg)
                .password(passwordEncoder.encode(userSignupReq.getPassword()))
                .nickname(userSignupReq.getNickname())
                .build();
        userRepository.save(user);
    }

    public Boolean checkDuplicateEmail(String email) {
        return userRepository.findByEmail(email).isEmpty();
    }

    public Boolean checkDuplicateNickname(String nickname) {
        return userRepository.findByNickname(nickname).isEmpty();
    }

    public void updateNickname(Long userId, String nickname) {
        if (userRepository.findByNickname(nickname).isEmpty()) {
            User user = userRepository.findById(userId).orElse(null);
            if (user != null) {
                user.updateNickname(nickname);
                userRepository.save(user);
            } else {
                throw new InvalidUserException(BaseResponseStatus.USER_NOT_FOUND);
            }
        } else {
            throw new InvalidUserException(BaseResponseStatus.USER_DUPLICATE_NICKNAME);
        }
    }

    public void updateImg(Long userId, String imgUrl) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            user.updateProfileImg(imgUrl);
            userRepository.save(user);
        } else {
            throw new InvalidUserException(BaseResponseStatus.USER_NOT_FOUND);
        }
    }
}
