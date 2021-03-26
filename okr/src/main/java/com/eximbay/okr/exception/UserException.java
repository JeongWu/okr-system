package com.eximbay.okr.exception;

public class UserException extends RuntimeException{
    public UserException(String message){
        super(message);
    }

    public UserException(Throwable throwable){
        super(throwable);
    }
}
