package com.eximbay.okr.dto.api;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorField {
    private String fieldName;
    private String message;
}
