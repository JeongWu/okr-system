package com.eximbay.okr.dto.memberhistory;

import java.time.Instant;

import com.eximbay.okr.dto.member.MemberDto;
import com.eximbay.okr.listener.AbstractAuditableDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MemberHistoryDto extends AbstractAuditableDto {
private Integer historySeq;
        private String name;
        private String localName;
        private MemberDto member;
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
        private String justification;
        private String editCompanyOkrFlag;
        
        
        public void createMemberHistoryDto(String joiningDate ,String retirementDate, String editCompanyOkrFlag,
        String useFlag, String adminFlag, String eamil) {

            String joining = joiningDate.replace("-", "");
            this.setJoiningDate(joining);
    
            String retire = retirementDate.replace("-", "");
            this.setRetirementDate(retire);

            // domain delete
            int index= eamil.indexOf("@"); 
            String exceptDomain = eamil.substring(0, index);
            this.setMemberId(exceptDomain);
            
            String UseFlag = (useFlag == null) ? "N" : "Y";
            this.setUseFlag(UseFlag);
            
            // Admin Flag 
            String AdminFlag = (adminFlag == null) ? "N" : "Y";
            this.setAdminFlag(AdminFlag);
    
            String Edit = (editCompanyOkrFlag == null) ? "N" : "Y";
            this.setEditCompanyOkrFlag(Edit);       
        }
}
