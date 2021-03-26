package com.eximbay.okr.dto;

import lombok.Data;

@Data
public class AssessmentDto {

    private String groupCode;
    private String code;
    private String groupName;
    private String codeName;
    private int codeValue;
    private int beginRange;
    private int endRange;
    private String discription;   
    
}
