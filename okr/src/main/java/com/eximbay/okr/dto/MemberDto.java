package com.eximbay.okr.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class MemberDto {

    private Integer memberSeq;
    private String name;
    private String localName;
    private String memberId;
    private String email;
    private String contactPhone;
    private String password;
    private String introduction;
    private String image;
    private String position;
    private int level;
    private String joiningDate;
    private int career;
    private String retirementDate;
    private Instant lastPasswordChange;
    private String passwordTempFlag;
    private int passwordErrorCount;
    private Instant lassLoginDate;
    private String adminFlag;
    private String adminAccessIp;
    private String useFlag;
}
