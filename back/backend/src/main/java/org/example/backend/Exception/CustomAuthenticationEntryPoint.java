package org.example.backend.Exception;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.backend.Common.BaseResponse;
import org.example.backend.Common.BaseResponseStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final Gson gson = new Gson();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");
        PrintWriter out = response.getWriter();
        String jsonResponse = gson.toJson(new BaseResponse<>(BaseResponseStatus.USER_NOT_LOGIN));
        out.print(jsonResponse);
    }
}
