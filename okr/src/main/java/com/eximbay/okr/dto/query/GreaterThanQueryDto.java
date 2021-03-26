package com.eximbay.okr.dto.query;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GreaterThanQueryDto {
    private String field;
    private Comparable value;
    private boolean isEqual = false;

    public GreaterThanQueryDto(String field, Comparable value) {
        this.field = field;
        this.value = value;
    }
}
