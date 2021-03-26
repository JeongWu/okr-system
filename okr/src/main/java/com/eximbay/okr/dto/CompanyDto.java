package com.eximbay.okr.dto;

import com.eximbay.okr.listener.AbstractAuditableDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CompanyDto extends AbstractAuditableDto {

    private Integer companySeq;
    private String name;
    private String localName;
    private String logo;
    private String domain;
    private String googleSignIn;
    private String googleClientId;
    private String googleClientSecretKey;
}
