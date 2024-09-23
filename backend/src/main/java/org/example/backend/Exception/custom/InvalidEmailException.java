package org.example.backend.Exception.custom;

import org.example.backend.Common.BaseResponseStatus;

public class InvalidEmailException extends InvalidCustomException {
    public InvalidEmailException(BaseResponseStatus status){
        super(status);
    }
}
