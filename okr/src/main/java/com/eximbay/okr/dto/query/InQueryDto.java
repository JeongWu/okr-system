package com.eximbay.okr.dto.query;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class InQueryDto<T> {
    private String field;
    private List<T> in;
}
