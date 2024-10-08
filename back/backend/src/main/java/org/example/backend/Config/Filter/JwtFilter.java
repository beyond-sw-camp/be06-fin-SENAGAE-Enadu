package org.example.backend.Config.Filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.backend.Security.CustomUserDetails;
import com.example.common.User.Model.Entity.User;
import org.example.backend.Util.JwtUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("ATOKEN")) {
                    if (jwtUtil.isExpired(cookie.getValue())) {
                        Cookie expiredCookie = jwtUtil.removeCookie();
                        response.addCookie(expiredCookie);
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        filterChain.doFilter(request, response);
                        return;
                    }
                    authorization = cookie.getValue();
                    break;
                }
            }
        }

        if (authorization == null) {
            filterChain.doFilter(request, response);
            return;
        }

        Long id = jwtUtil.getId(authorization);
        String email = jwtUtil.getEmail(authorization);
//        String role = jwtUtil.getRole(authorization); 추후 role 추가

        User user = User.builder()
                .id(id)
                .email(email)
//                .role(role)
                .build();

        CustomUserDetails customUserDetails = new CustomUserDetails(user);

        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null,
                customUserDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}