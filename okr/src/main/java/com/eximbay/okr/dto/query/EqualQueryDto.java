package com.eximbay.okr.dto.query;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EqualQueryDto {
    private String field;
    private Object value;
}
