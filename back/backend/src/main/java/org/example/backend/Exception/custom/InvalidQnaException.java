package org.example.backend.Exception.custom;

import org.example.backend.Common.BaseResponseStatus;

public class InvalidQnaException extends InvalidCustomException {

    public InvalidQnaException(BaseResponseStatus status) {
        super(status);
    }
}
