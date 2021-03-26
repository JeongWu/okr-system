package com.eximbay.okr.dto;

import com.eximbay.okr.listener.AbstractAuditableDto;
import lombok.Data;

@Data
public class DivisionHistoryDto extends AbstractAuditableDto {

    private Integer historySeq;
    private DivisionDto division;
    private CompanyDto company;
    private String name;
    private String localName;
    private String useFlag;
    private String justification;
}
