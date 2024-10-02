package org.example.backend.Exception.custom;

import org.example.backend.Common.BaseResponseStatus;

public class InvalidChatException extends InvalidCustomException{

    public InvalidChatException(BaseResponseStatus status) {
        super(status);
    }
}
