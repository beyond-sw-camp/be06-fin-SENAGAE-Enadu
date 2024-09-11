package org.example.backend.Exception;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.backend.Common.BaseResponse;
import org.example.backend.Common.BaseResponseStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private final Gson gson = new Gson();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        BaseResponse baseResponse = new BaseResponse<>(BaseResponseStatus.FAIL);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        if (exception instanceof OAuth2AuthenticationException) {
            OAuth2Error error = ((OAuth2AuthenticationException) exception).getError();
            String errorCode = error.getErrorCode();

            if ("No Email in GitHub".equals(errorCode)) {
                baseResponse = new BaseResponse<>(BaseResponseStatus.USER_EMAIL_NOT_FOUND_IN_GITHUB);
            } else if ("Type Error".equals(errorCode)) {
                baseResponse = new BaseResponse<>(BaseResponseStatus.USER_INVALID_TYPE);
            }
        }

        String jsonResponse = gson.toJson(baseResponse);
        response.getWriter().write(jsonResponse);
    }
}
