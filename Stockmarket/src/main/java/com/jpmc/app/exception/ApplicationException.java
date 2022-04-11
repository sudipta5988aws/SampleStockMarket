package com.jpmc.app.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationException extends Exception{
    Integer errorCode;
    String errorMessage;

    public ApplicationException(Integer errorCode, String errorMessage){
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
