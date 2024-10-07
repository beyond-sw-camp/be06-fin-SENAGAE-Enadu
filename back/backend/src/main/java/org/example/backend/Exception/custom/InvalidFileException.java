package org.example.backend.Exception.custom;

import org.example.backend.Common.BaseResponseStatus;

public class InvalidFileException extends InvalidCustomException {

    public InvalidFileException(BaseResponseStatus status) {
        super(status);
    }
}
