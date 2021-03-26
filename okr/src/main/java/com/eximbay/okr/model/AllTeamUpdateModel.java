package com.eximbay.okr.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.eximbay.okr.dto.DivisionDto;
import com.eximbay.okr.entity.Division;
import com.eximbay.okr.enumeration.TeamType;

@Data
public class AllTeamUpdateModel {
    private Integer teamSeq;

    private String name;

    private String localName;

    private DivisionDto division;

    private TeamType teamType;
    
    private String image;

    private String introduction;


    
}
