package org.example.backend.Exception.custom;

import org.example.backend.Common.BaseResponseStatus;

public class InvalidMainException extends InvalidCustomException{
    public InvalidMainException(BaseResponseStatus status){
        super(status);
    }
}
