package org.example.backend.Exception.custom;

import org.example.backend.Common.BaseResponseStatus;

public class InvalidUserException extends InvalidCustomException {
    public InvalidUserException(BaseResponseStatus status) {
        super(status);
    }
}