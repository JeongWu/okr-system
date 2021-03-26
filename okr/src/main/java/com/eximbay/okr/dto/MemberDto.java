package com.eximbay.okr.dto;

import com.eximbay.okr.listener.AbstractAuditableDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
public class MemberDto extends AbstractAuditableDto {

    private Integer memberSeq;
    private String name;
    private String localName;
    private String memberId;
    private String email;
    private String contactPhone;
    private String introduction;
    private String image;
    private String position;
    private int level;
    private String joiningDate;
    private int career;
    private String retirementDate;
    private String adminFlag;
    private String adminAccessIp;
    private String editCompanyOkrFlag;
    private String justification;
    private String useFlag;

    private List<TeamMemberDto> teamMembers = new ArrayList<>();

    public void createMemberDto(String joiningDate ,String retirementDate, String editCompanyOkrFlag,
    String useFlag, String adminFlag, String eamil) {
        
        String joining = joiningDate.replace("-", "");
        this.setJoiningDate(joining);

        String retire = retirementDate.replace("-", "");
        this.setRetirementDate(retire);
        
        // domain delete
        int index= eamil.indexOf("@"); 
        String exceptDomain = eamil.substring(0, index);
        this.setMemberId(exceptDomain);
        
        // Active 
        String UseFlag = (useFlag == null) ? "N" : "Y";
        this.setUseFlag(UseFlag);
        
        // Admin Flag 
        String AdminFlag = (adminFlag == null) ? "N" : "Y";
        this.setAdminFlag(AdminFlag);

        String Edit = (editCompanyOkrFlag == null) ? "N" : "Y";
        this.setEditCompanyOkrFlag(Edit);
            
    }
}