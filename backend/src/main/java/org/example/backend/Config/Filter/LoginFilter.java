package org.example.backend.Config.Filter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.backend.Security.CustomUserDetails;
import org.example.backend.Util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String requestBody;

        try {
            requestBody = request.getReader().lines().collect(Collectors.joining());
        } catch (IOException e) {
            throw new RuntimeException("Failed to read request body", e);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> requestData;

        try {
            requestData = objectMapper.readValue(requestBody, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse JSON", e);
        }

        String username = requestData.get("email");
        String password = requestData.get("password");

        if (username == null || password == null) {
            throw new RuntimeException("email or password is null");
        }

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, null);

        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        CustomUserDetails user = (CustomUserDetails) authResult.getPrincipal();
        Collection<String> authorities = authResult.getAuthorities().stream()
                .map(authority -> authority.getAuthority())
                .toList();

        String role = authorities.iterator().next();
        String username = user.getUsername();
        Long userId = user.getUserId();

        String token = jwtUtil.createToken(userId, username, role);
        Cookie jwtCookie = jwtUtil.createCookie(token);
        response.addCookie(jwtCookie);
        response.addHeader("Authorization", "Bearer " + token);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("userId", userId);

        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(responseData));
    }
}