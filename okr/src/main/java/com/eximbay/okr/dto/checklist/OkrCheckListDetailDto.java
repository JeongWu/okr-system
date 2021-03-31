package com.eximbay.okr.dto.checklist;

import com.eximbay.okr.dto.checklist.CheckListDto;
import com.eximbay.okr.dto.keyresult.KeyResultDto;
import com.eximbay.okr.dto.okrchecklistgroup.OkrChecklistGroupDto;

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
