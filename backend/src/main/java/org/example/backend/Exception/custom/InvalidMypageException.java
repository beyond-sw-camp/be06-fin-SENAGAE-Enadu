package org.example.backend.Exception.custom;

import org.example.backend.Common.BaseResponseStatus;

public class InvalidMypageException extends InvalidCustomException {
    public InvalidMypageException(BaseResponseStatus status) {
        super(status);
    }
}
