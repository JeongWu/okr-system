package com.eximbay.okr.dto.query;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LikeQueryDto {
    private String field;
    private String value;
}
