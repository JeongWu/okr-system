package com.eximbay.okr.dto.division;

import com.eximbay.okr.constant.FlagOption;
import com.eximbay.okr.dto.company.CompanyDto;
import com.eximbay.okr.listener.AbstractAuditableDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class DivisionDto extends AbstractAuditableDto {
    private Integer divisionSeq;
    private CompanyDto company;
    private String name;
    private String localName;
    private String useFlag = FlagOption.Y;
}
