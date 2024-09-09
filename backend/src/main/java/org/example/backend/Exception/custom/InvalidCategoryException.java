package org.example.backend.Exception.custom;

import org.example.backend.Common.BaseResponseStatus;

public class InvalidCategoryException extends InvalidCustomException {

    public InvalidCategoryException(BaseResponseStatus status) {
        super(status);
    }
}

