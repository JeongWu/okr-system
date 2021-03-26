package com.eximbay.okr.exception;

import lombok.Data;
import org.springframework.validation.FieldError;

import java.util.List;

@Data
public class InvalidRestInputException extends RuntimeException{
    private List<FieldError> fieldErrors;

    public InvalidRestInputException(String message){
        super(message);
    }

    public InvalidRestInputException(List<FieldError> fieldErrors) {
        super();
        this.fieldErrors = fieldErrors;
    }
}
