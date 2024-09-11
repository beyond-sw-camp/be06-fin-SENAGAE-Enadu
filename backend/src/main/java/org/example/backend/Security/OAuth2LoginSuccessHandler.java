package org.example.backend.Security;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.backend.Util.JwtUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;

    public OAuth2LoginSuccessHandler(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

        String jwtToken = jwtUtil.createToken(oAuth2User.getUserId(), oAuth2User.getName(), "ROLE_USER");
        Cookie jwtCookie = jwtUtil.createCookie(jwtToken);
        response.addCookie(jwtCookie);

        String redirectUrl = "http://localhost:8081/";
        response.sendRedirect(redirectUrl);
    }
}