package com.eximbay.okr.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.eximbay.okr.dto.division.DivisionDto;
import com.eximbay.okr.enumeration.TeamType;

import org.springframework.web.multipart.MultipartFile;


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

    private MultipartFile imageFile;
    @NotNull
    private String introduction;
    @NotNull
    private boolean useFlag;
    @NotBlank
    private String justification;
}
