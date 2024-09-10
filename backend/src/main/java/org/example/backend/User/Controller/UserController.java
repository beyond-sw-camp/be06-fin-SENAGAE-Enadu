package org.example.backend.User.Controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.backend.Common.BaseResponse;
import org.example.backend.Util.JwtUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final JwtUtil jwtUtil;
    @PostMapping("/logout")
    public BaseResponse<String> logout(HttpServletResponse response) {
        Cookie expiredCookie = jwtUtil.removeCookie();
        response.addCookie(expiredCookie);
        return new BaseResponse<>("로그아웃");
    }
}
