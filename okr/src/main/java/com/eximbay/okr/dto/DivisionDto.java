package com.eximbay.okr.dto;

import com.eximbay.okr.constant.FlagOption;
import com.eximbay.okr.listener.AbstractAuditableDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Set;

@Data
public class DivisionDto extends AbstractAuditableDto {
    private Integer divisionSeq;
    private CompanyDto company;
    private String name;
    private String localName;
    private String useFlag = FlagOption.Y;
}
