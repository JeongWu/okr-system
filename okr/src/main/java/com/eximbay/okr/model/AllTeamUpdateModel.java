package com.eximbay.okr.model;

import lombok.Data;


import com.eximbay.okr.dto.DivisionDto;
import com.eximbay.okr.enumeration.TeamType;

import org.springframework.web.multipart.MultipartFile;

@Data
public class AllTeamUpdateModel {
    private Integer teamSeq;

    private String name;

    private String localName;

    private DivisionDto division;

    private TeamType teamType;
    
    private String image;

    private MultipartFile imageFile;

    private String introduction;


    
}
