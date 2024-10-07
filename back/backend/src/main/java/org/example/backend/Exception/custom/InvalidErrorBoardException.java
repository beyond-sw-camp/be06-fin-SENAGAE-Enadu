package org.example.backend.Exception.custom;

import org.example.backend.Common.BaseResponseStatus;

public class InvalidErrorBoardException extends InvalidCustomException {
    public InvalidErrorBoardException(BaseResponseStatus status){
        super(status);
    }
}
