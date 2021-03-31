package com.eximbay.okr.dto.evaluationcriteria;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class EvaluationCriteriaDto {

    private String groupCode;
    private String code;
    private String groupName;
    private String codeName;
    private BigDecimal codeValue;
    private BigDecimal beginRange;
    private BigDecimal endRange;
    private String description;
    
}


