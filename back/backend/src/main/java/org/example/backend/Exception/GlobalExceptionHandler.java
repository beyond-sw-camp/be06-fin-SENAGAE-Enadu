package org.example.backend.Exception;


import org.example.backend.Common.BaseResponse;
import org.example.backend.Exception.custom.InvalidCustomException;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(value = 1)
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse<String> handleGlobalException(InvalidCustomException e) {
        return new BaseResponse<>(e.getStatus());
    }
}
