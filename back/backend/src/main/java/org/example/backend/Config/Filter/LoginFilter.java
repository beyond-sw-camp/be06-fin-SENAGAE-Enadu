package org.example.backend.Config.Filter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.backend.Common.BaseResponseStatus;
import org.example.backend.Exception.custom.InvalidUserException;
import org.example.backend.Security.CustomUserDetails;
import org.example.backend.Util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
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
            throw new InvalidUserException(BaseResponseStatus.USER_INVALID_REQUEST_BODY);
        }


        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> requestData;

        try {
            requestData = objectMapper.readValue(requestBody, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new InvalidUserException(BaseResponseStatus.USER_JSON_PARSE_ERROR);
        }

        String username = requestData.get("email");
        String password = requestData.get("password");

        if (username == null || password == null) {
            throw new InvalidUserException(BaseResponseStatus.USER_EMAIL_OR_PASSWORD_NULL);
        }

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, null);

        try {
            Authentication authentication = authenticationManager.authenticate(authToken);
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

            if (!userDetails.isVerified()) {
                throw new InvalidUserException(BaseResponseStatus.USER_EMAIL_NOT_VERIFIED);
            }

            return authentication;

        } catch (DisabledException e) {
            throw new InvalidUserException(BaseResponseStatus.USER_INACTIVE_ACCOUNT);
        } catch (BadCredentialsException e) {
            throw new InvalidUserException(BaseResponseStatus.USER_INVALID_CREDENTIALS);
        }
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

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("userId", userId);

        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(responseData));
    }
}