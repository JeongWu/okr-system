package com.eximbay.okr.dto;

import lombok.Data;

@Data
public class CodeListDto {
   
    private String group_code;
    private String code;

    private String codeName;

    private String description;

    private int sortOrder;

    private String useFlag;
}
