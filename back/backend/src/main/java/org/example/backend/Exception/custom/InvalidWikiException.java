package org.example.backend.Exception.custom;

import org.example.backend.Common.BaseResponseStatus;

public class InvalidWikiException extends InvalidCustomException {

    public InvalidWikiException(BaseResponseStatus status) {
        super(status);
    }
}
