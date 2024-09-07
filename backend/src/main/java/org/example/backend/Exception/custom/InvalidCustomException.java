package org.example.backend.Exception.custom;

import lombok.Getter;
import org.example.backend.Common.BaseResponseStatus;

@Getter
public class InvalidCustomException extends RuntimeException {
    private final BaseResponseStatus status;

    public InvalidCustomException(BaseResponseStatus status) {
        this.status = status;
    }
}
