package com.eximbay.okr.exception;

public class RestUserException extends RuntimeException{
    public RestUserException(String message){
        super(message);
    }
    public RestUserException(Throwable throwable){
        super(throwable);
    }
}
