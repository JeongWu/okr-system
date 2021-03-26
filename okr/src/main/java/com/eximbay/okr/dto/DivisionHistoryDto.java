package com.eximbay.okr.dto;

import lombok.Data;

@Data
public class DivisionHistoryDto {

    private Integer historySeq;
    private DivisionDto division;
    private CompanyDto company;
    private String name;
    private String localName;
    private String useFlag;
    private String justification;
}
