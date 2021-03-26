package com.eximbay.okr.handler;

import com.eximbay.okr.constant.ErrorMessages;
import com.eximbay.okr.exception.UserException;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.Optional;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(UserException.class)
    public String handleUserException(UserException e, Model model){
        String message = Optional.ofNullable(e.getCause()).orElse(e).getMessage();
        model.addAttribute("message",message);
        return "error/userErrorPage";
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(FileSizeLimitExceededException.class)
    public String fileSizeLimitExceededException(Exception e, Model model){
        model.addAttribute("message", ErrorMessages.fileSizeLimitExceeded);
        return "error/userErrorPage";
    }
}
