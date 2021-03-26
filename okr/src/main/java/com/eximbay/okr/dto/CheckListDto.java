package com.eximbay.okr.dto;

import java.time.Instant;

import com.eximbay.okr.enumeration.CheckListType;

import groovy.transform.EqualsAndHashCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckListDto {

    private Integer checkListSeq;
    private String question;
    private CheckListType type;
    private String useFlag;
    protected String createdBy = "system";
    protected String updatedBy = "system";
    protected Instant createdDate = Instant.now();
 
}
