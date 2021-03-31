package com.eximbay.okr.dto.api;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ErrorResponse {
    private String message = "";
    private List<ErrorField> errorFields = new ArrayList<>();

    public ErrorResponse() {}

    public ErrorResponse(String message) {
        this.message = message;
    }
}
