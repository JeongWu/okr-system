package com.eximbay.okr.model;

import com.eximbay.okr.constant.FlagOption;
import com.eximbay.okr.dto.company.CompanyDto;
import com.eximbay.okr.dto.member.MemberDto;
import com.eximbay.okr.dto.team.TeamDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@ToString(exclude = { "company", "teams", "members"})
@EqualsAndHashCode(exclude = { "company", "teams", "members"})
public class DivisionForDivisionsModel {

    private Integer divisionSeq;
    private CompanyDto company;
    private String name;
    private String localName;
    private String useFlag = FlagOption.Y;
    private List<TeamDto> teams;
    private List<MemberDto> members;
}
