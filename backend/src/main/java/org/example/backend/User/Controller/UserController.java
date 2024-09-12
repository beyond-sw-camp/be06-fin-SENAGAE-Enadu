package org.example.backend.User.Controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.Common.BaseResponse;
import org.example.backend.File.Service.CloudFileUploadService;
import org.example.backend.User.Model.Req.UserSignupReq;
import org.example.backend.User.Service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.example.backend.Util.JwtUtil;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final CloudFileUploadService cloudFileUploadService;
    private final JwtUtil jwtUtil;
    @RequestMapping(method= RequestMethod.POST, value="/signup")
    public BaseResponse<String> signup(
            @RequestPart UserSignupReq userSignupReq,
            @RequestPart MultipartFile profileImg) {
        String profileImgUrl;
        if(profileImg == null || profileImg.isEmpty()){
            profileImgUrl = "https://dayun2024-s3.s3.ap-northeast-2.amazonaws.com/IMAGE/2024/09/11/0d7ca962-ccee-4fbb-9b5d-f5deec5808c6";
        } else {
            profileImgUrl = cloudFileUploadService.uploadImg(profileImg);
        }
        userService.signup(userSignupReq, profileImgUrl);
        return new BaseResponse<>();
    }

    @PostMapping("/logout")
    public BaseResponse<String> logout(HttpServletResponse response) {
        Cookie expiredCookie = jwtUtil.removeCookie();
        response.addCookie(expiredCookie);
        return new BaseResponse<>("로그아웃");
    }
    @GetMapping("/duplicate/email")
    public BaseResponse<Boolean> duplicateEmail(@RequestParam String email) {
        return new BaseResponse<>(userService.checkDuplicateEmail(email));
    }
    @GetMapping("/duplicate/nickname")
    public BaseResponse<Boolean> duplicateName(@RequestParam String nickname) {
        return new BaseResponse<>(userService.checkDuplicateNickname(nickname));
    }
}
