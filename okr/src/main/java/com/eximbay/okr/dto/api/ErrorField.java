package com.eximbay.okr.dto.api;

import lombok.*;

@Data
@AllArgsConstructor
public class ErrorField {
    private String fieldName;
    private String message;
}
