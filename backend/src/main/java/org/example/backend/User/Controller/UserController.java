package org.example.backend.User.Controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.backend.Common.BaseResponse;
import org.example.backend.User.Service.UserService;
import org.example.backend.Util.JwtUtil;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final JwtUtil jwtUtil;
    private final UserService userService;
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
