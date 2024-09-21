package org.example.backend.User.Service;

import lombok.RequiredArgsConstructor;
import org.example.backend.Common.BaseResponseStatus;
import org.example.backend.Exception.custom.InvalidEmailException;
import org.example.backend.Exception.custom.InvalidUserException;
import org.example.backend.User.Model.Entity.User;
import org.example.backend.User.Model.Req.UpdateUserPasswordReq;
import org.example.backend.User.Model.Req.UserSignupReq;
import org.example.backend.User.Model.Res.GetUserInfoRes;
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

    public void updatePassword(Long userId, UpdateUserPasswordReq updateUserPasswordReq) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            if (passwordEncoder.matches(updateUserPasswordReq.getOldPassword(), user.getPassword())) {
                String encodedNewPassword = passwordEncoder.encode(updateUserPasswordReq.getNewPassword());
                user.updatePassword(encodedNewPassword);
                userRepository.save(user);
            } else {
                throw new InvalidUserException(BaseResponseStatus.USER_PASSWORDS_DO_NOT_MATCH);
            }
        } else {
            throw new InvalidUserException(BaseResponseStatus.USER_NOT_FOUND);
        }
    }

    public Boolean checkPassword(Long userId, String password) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            return passwordEncoder.matches(password, user.getPassword());
        } else {
            throw new InvalidUserException(BaseResponseStatus.USER_NOT_FOUND);
        }
    }

    public void disableUser(Long userId, String password) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            if (passwordEncoder.matches(password, user.getPassword())) {
                user.updateEnable(false);
            } else {
                throw new InvalidUserException(BaseResponseStatus.USER_PASSWORDS_DO_NOT_MATCH);
            }
        } else {
            throw new InvalidUserException(BaseResponseStatus.USER_NOT_FOUND);
        }
    }

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

    public void updateVerifiedStatus(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new InvalidEmailException(BaseResponseStatus.EMAIL_VERIFY_FAIL));
        user.updateVerified(true);
        userRepository.save(user);
    }
}
