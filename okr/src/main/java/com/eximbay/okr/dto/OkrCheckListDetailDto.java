package com.eximbay.okr.dto;

import com.eximbay.okr.dto.okrChecklistGroup.OkrChecklistGroupDto;

import lombok.Data;

@Data
public class OkrCheckListDetailDto {

    private Integer detailSeq;
    private OkrChecklistGroupDto okrChecklistGroup;
    private CheckListDto checkList;
    private KeyResultDto keyResult;
    private String sourceKeyResult;
    private String question;
    private String answerCode;    
}
