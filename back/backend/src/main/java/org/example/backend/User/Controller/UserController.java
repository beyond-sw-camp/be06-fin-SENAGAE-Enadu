package org.example.backend.User.Controller;

import static org.example.backend.Common.BaseResponseStatus.USER_INVALID_NICKNAME;

import lombok.RequiredArgsConstructor;
import org.example.backend.Common.BaseResponse;
import org.example.backend.Common.BaseResponseStatus;
import org.example.backend.EmailVerify.Service.EmailVerifyService;
import org.example.backend.Exception.custom.InvalidUserException;
import org.example.backend.File.Service.CloudFileUploadService;
import org.example.backend.Security.CustomUserDetails;
import org.example.backend.User.Model.Req.UpdateUserPasswordReq;
import org.example.backend.User.Model.Req.UserSignupReq;
import org.example.backend.User.Service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.example.backend.Util.JwtUtil;

import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final CloudFileUploadService cloudFileUploadService;
    private final JwtUtil jwtUtil;
    private final EmailVerifyService emailVerifyService;
    private static final String EMAIL_PATTERN = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    private static final String NICKNAME_PATTERN = "^[a-zA-Z0-9가-힣]+$";

    @PostMapping("/signup")
    public BaseResponse<String> signup(
            @RequestPart UserSignupReq userSignupReq,
            @RequestPart(required=false) MultipartFile profileImg) {

        String email = userSignupReq.getEmail().trim();
        String password = userSignupReq.getPassword().trim();
        String nickname = userSignupReq.getNickname().trim();

        if (email.isBlank() || password.isBlank() || nickname.isBlank()) {
            throw new InvalidUserException(BaseResponseStatus.USER_INVALID_INPUT);
        }

        if (email.length() > 100 || !email.matches(EMAIL_PATTERN)) {
            throw new InvalidUserException(BaseResponseStatus.USER_INVALID_EMAIL_FORMAT);
        }

        if (password.length() < 8) {
            throw new InvalidUserException(BaseResponseStatus.USER_INVALID_PASSWORD);
        }

        if (nickname.length() > 45 || !nickname.matches(NICKNAME_PATTERN)) {
            throw new InvalidUserException(USER_INVALID_NICKNAME);
        }

        String profileImgUrl;
        if (profileImg == null || profileImg.isEmpty()){
            profileImgUrl = "https://dayun2024-s3.s3.ap-northeast-2.amazonaws.com/IMAGE/2024/09/11/0d7ca962-ccee-4fbb-9b5d-f5deec5808c6";
        } else {
            profileImgUrl = cloudFileUploadService.uploadImg(profileImg);
        }

        if (!userService.checkDuplicateEmail(email)) {
            throw new InvalidUserException(BaseResponseStatus.USER_DUPLICATE_EMAIL);
        }

        if (!userService.checkDuplicateNickname(nickname)) {
            throw new InvalidUserException(BaseResponseStatus.USER_DUPLICATE_NICKNAME);
        }

        try {
            userService.signup(userSignupReq, profileImgUrl);
            emailVerifyService.sendEmail(email);
        } catch (Exception e) {
            throw new InvalidUserException(BaseResponseStatus.USER_EMAIL_SEND_FAILED);
        }

        return new BaseResponse<>();
    }

    @PostMapping("/logout")
    public BaseResponse<String> logout(HttpServletResponse response) {
        Cookie expiredCookie = jwtUtil.removeCookie();
        response.addCookie(expiredCookie);
        return new BaseResponse<>();
    }

    @GetMapping("/duplicate/email")
    public BaseResponse<Boolean> duplicateEmail(String email) {
        if (email.length() > 100 || !email.matches(EMAIL_PATTERN)) {
            throw new InvalidUserException(BaseResponseStatus.USER_INVALID_EMAIL_FORMAT);
        }
        return new BaseResponse<>(userService.checkDuplicateEmail(email));
    }

    @GetMapping("/duplicate/nickname")
    public BaseResponse<Boolean> duplicateName(String nickname) {
        if (nickname.length() > 45 || !nickname.matches(NICKNAME_PATTERN)) {
            throw new InvalidUserException(USER_INVALID_NICKNAME);
        }
        return new BaseResponse<>(userService.checkDuplicateNickname(nickname));
    }

    @PatchMapping("/nickname")
    public BaseResponse<String> updateNickname(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody Map<String, String> nicknameMap ) {
        if (nicknameMap.get("nickname").length() > 45 || !nicknameMap.get("nickname").matches(NICKNAME_PATTERN)) {
            throw new InvalidUserException(USER_INVALID_NICKNAME);
        }
        userService.updateNickname(customUserDetails.getUserId(), nicknameMap.get("nickname"));
        return new BaseResponse<>();
    }

    @PatchMapping("/img")
    public BaseResponse<String> updateImg(@AuthenticationPrincipal CustomUserDetails customUserDetails, MultipartFile imgFile) {
        String imgUrl = cloudFileUploadService.uploadImg(imgFile);
        userService.updateImg(customUserDetails.getUserId(), imgUrl);
        return new BaseResponse<>(imgUrl);
    }

    @PatchMapping("/password")
    public BaseResponse<String> updatePassword(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody UpdateUserPasswordReq updateUserPasswordReq) {
        if(updateUserPasswordReq.getNewPassword().equals(updateUserPasswordReq.getConfirmPassword())){
            if (updateUserPasswordReq.getNewPassword().length() < 8) {
                throw new InvalidUserException(BaseResponseStatus.USER_INVALID_PASSWORD);
            }
            userService.updatePassword(customUserDetails.getUserId(), updateUserPasswordReq);
        } else {
            throw new InvalidUserException(BaseResponseStatus.USER_NEW_PASSWORDS_DO_NOT_MATCH);
        }
        return new BaseResponse<>();
    }

    @PostMapping("/password/verify")
    public BaseResponse<Boolean> checkPassword(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody Map<String, String> passwordMap) {
        return new BaseResponse<>(userService.checkPassword(customUserDetails.getUserId(), passwordMap.get("password")));
    }

    @PatchMapping("/quit")
    public BaseResponse<String> quit(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody Map<String, String> passwordMap, HttpServletResponse response) {
        userService.disableUser(customUserDetails.getUserId(), passwordMap.get("password"));
        Cookie expiredCookie = jwtUtil.removeCookie();
        response.addCookie(expiredCookie);
        return new BaseResponse<>();
    }

    @PatchMapping("/oauth/quit")
    public BaseResponse<String> oauthQuit(@AuthenticationPrincipal CustomUserDetails customUserDetails, @CookieValue(value = "ATOKEN", required = false) String jwtToken, HttpServletResponse response) {
        userService.disableSocialUser(customUserDetails.getUserId(), jwtToken);
        Cookie expiredCookie = jwtUtil.removeCookie();
        response.addCookie(expiredCookie);
        return new BaseResponse<>();
    }
}
