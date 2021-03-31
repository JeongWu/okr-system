package com.eximbay.okr.dto.divisionhistory;

import com.eximbay.okr.dto.company.CompanyDto;
import com.eximbay.okr.dto.division.DivisionDto;
import com.eximbay.okr.listener.AbstractAuditableDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
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
