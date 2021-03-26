package com.eximbay.okr.api.response;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class RestResponse {
    private boolean success;
    private String message;
    private Map<String, String> errors = new HashMap<>();

    public static RestResponse success() {
        RestResponse response = new RestResponse();
        response.success = true;
        return response;
    }

    public static RestResponse error() {
        RestResponse response = new RestResponse();
        response.success = false;
        return response;
    }

    public RestResponse addError(String fieldName, String message) {
        this.errors.put(fieldName, message);
        return this;
    }
}
