package com.eximbay.okr.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.eximbay.okr.dto.DivisionDto;
import com.eximbay.okr.entity.Division;
import com.eximbay.okr.enumeration.TeamType;


@Data
public class TeamUpdateFormModel {
    @NotNull
    private Integer teamSeq;
    @NotBlank
    private String name;
    @NotBlank
    private String localName;
    
	private DivisionDto divisionDto;
    @NotNull
    private TeamType teamType;
    
    private String image;
    @NotNull
    private String introduction;
    @NotNull
    private boolean useFlag;
    @NotBlank
    private String justification;
}
