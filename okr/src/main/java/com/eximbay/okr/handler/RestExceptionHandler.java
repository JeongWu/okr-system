package com.eximbay.okr.handler;

import com.eximbay.okr.dto.api.*;
import com.eximbay.okr.exception.InvalidRestInputException;
import com.eximbay.okr.exception.RestUserException;
import org.springframework.http.*;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@ControllerAdvice
@RestController
public class RestExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(InvalidRestInputException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorResponse handleUserException(InvalidRestInputException e){
        return buildErrorResponse(e.getFieldErrors());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(RestUserException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorResponse generalException(RestUserException e){
        return new ErrorResponse(Optional.ofNullable(e.getCause()).orElse(e).getMessage());
    }

    private ErrorResponse buildErrorResponse(List<FieldError> errors){
        ErrorResponse response = new ErrorResponse();
        String message = "There are invalid inputs: ";
        for (FieldError error: errors){
            message = message + error.getField() + ",";
            response.getErrorFields().add(new ErrorField(error.getField(), error.getDefaultMessage()));
        }
        response.setMessage(message);
        return response;
    }
}
