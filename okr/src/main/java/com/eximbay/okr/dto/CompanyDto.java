package com.eximbay.okr.dto;

import com.eximbay.okr.entity.Division;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Set;

@Data
public class CompanyDto {

    private Integer companySeq;
    private String name;
    private String localName;
    private String logo;
    private String domain;
    private String googleSignIn;
    private String googleClientId;
    private String googleClientSecretKey;
}
