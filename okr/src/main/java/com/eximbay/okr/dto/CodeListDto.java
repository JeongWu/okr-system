package com.eximbay.okr.dto;

import com.eximbay.okr.constant.FlagOption;
import com.eximbay.okr.entity.CodeGroup;
import com.eximbay.okr.entity.CodeListId;

import lombok.Data;

@Data
public class CodeListDto {
    
    private CodeGroup groupCode;
    private CodeListId codeListId;
    private String codeName;
    private String description;
    private int sortOrder;
    private String useFlag = FlagOption.Y;
}
