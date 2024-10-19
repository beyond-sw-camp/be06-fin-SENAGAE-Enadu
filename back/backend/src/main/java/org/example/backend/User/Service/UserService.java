package org.example.backend.User.Service;

import static org.example.backend.Common.BaseResponseStatus.USER_INVALID_NICKNAME;

import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.example.backend.Common.BaseResponseStatus;
import org.example.backend.Exception.custom.InvalidEmailException;
import org.example.backend.Exception.custom.InvalidUserException;
import com.example.common.User.Model.Entity.User;
import org.example.backend.User.Model.Req.UpdateUserPasswordReq;
import org.example.backend.User.Model.Req.UserSignupReq;
import com.example.common.User.Repository.UserRepository;
import org.example.backend.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import org.springframework.web.client.RestTemplate;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RestTemplate restTemplate;
    @Value("${spring.security.oauth2.client.registration.github.client-id}")
    private String CLIENT_ID;
    @Value("${spring.security.oauth2.client.registration.github.client-secret}")
    private String CLIENT_SECRET;
    private static final Pattern WHITESPACE_PATTERN = Pattern.compile("\\s");

    public void checkEmailPattern(String email) {
        if (WHITESPACE_PATTERN.matcher(email).find()) {
            throw new InvalidEmailException(BaseResponseStatus.USER_INVALID_INPUT_WITH_WHITESPACE);
        }
        if (email.length() > 100 || !email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new InvalidUserException(BaseResponseStatus.USER_INVALID_EMAIL_FORMAT);
        }
    }

    public void checkNicknamePattern(String nickname) {
        if (WHITESPACE_PATTERN.matcher(nickname).find()) {
            throw new InvalidUserException(BaseResponseStatus.USER_INVALID_INPUT_WITH_WHITESPACE);
        }
        if (nickname.length() > 50 || !nickname.matches("^[a-zA-Z0-9가-힣]+$")) {
            throw new InvalidUserException(USER_INVALID_NICKNAME);
        }
    }

    public void checkPasswordPattern(String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            throw new InvalidUserException(BaseResponseStatus.USER_NEW_PASSWORDS_DO_NOT_MATCH);
        }
        if (password.length() < 8) {
            throw new InvalidUserException(BaseResponseStatus.USER_INVALID_PASSWORD);
        }
        if (WHITESPACE_PATTERN.matcher(password).find()) {
            throw new InvalidUserException(BaseResponseStatus.USER_INVALID_INPUT_WITH_WHITESPACE);
        }
    }

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

    public void checkUserData(UserSignupReq userSignupReq) {
        String email = userSignupReq.getEmail();
        String password = userSignupReq.getPassword();
        String nickname = userSignupReq.getNickname();
        String confirmPassword = userSignupReq.getConfirmPassword();

        if (email.isBlank() || password.isBlank() || confirmPassword.isEmpty() || nickname.isBlank()) {
            throw new InvalidUserException(BaseResponseStatus.USER_INVALID_INPUT);
        }

        checkEmailPattern(email);
        checkPasswordPattern(password, confirmPassword);
        checkNicknamePattern(nickname);
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
                throw new InvalidUserException(BaseResponseStatus.USER_PASSWORD_DO_NOT_MATCH);
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
                userRepository.save(user);
            } else {
                throw new InvalidUserException(BaseResponseStatus.USER_PASSWORD_DO_NOT_MATCH);
            }
        } else {
            throw new InvalidUserException(BaseResponseStatus.USER_NOT_FOUND);
        }
    }

    public void updateVerifiedStatus(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new InvalidEmailException(BaseResponseStatus.EMAIL_VERIFY_FAIL));
        user.updateVerified(true);
        userRepository.save(user);
    }

    public void disableSocialUser(Long userId, String token) {
        User user = userRepository.findByIdAndEnableTrue(userId)
                .orElseThrow(() -> new InvalidUserException(BaseResponseStatus.USER_NOT_FOUND));

        String accessToken = jwtUtil.getAccessToken(token);
        if (accessToken == null) {
            throw new InvalidUserException(BaseResponseStatus.USER_ACCESS_TOKEN_NOT_FOUND);
        }

        String deleteUrl = "https://api.github.com/applications/" + CLIENT_ID + "/grant";
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(CLIENT_ID, CLIENT_SECRET);
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject requestBody = new JSONObject();
        requestBody.put("access_token", accessToken);

        HttpEntity<String> entity = new HttpEntity<>(requestBody.toString(), headers);

        ResponseEntity<Object> response = restTemplate.exchange(deleteUrl, HttpMethod.DELETE, entity, Object.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            user.updateEnable(false);
            userRepository.save(user);
        } else {
            throw new InvalidUserException(BaseResponseStatus.USER_FAIL);
        }
    }
}
