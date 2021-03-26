package com.eximbay.okr.dto;

import java.time.Instant;


import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class MemberHistoryDto {
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
	    private Instant updatedDate;
	    
	    public void createMemberHistoryDto(String useFlag, String adminFlag, String eamil, MultipartFile files) {
	        // domain delete
	        int index= eamil.indexOf("@"); 
	        String exceptDomain = eamil.substring(0, index);
	        this.setMemberId(exceptDomain);
	        
	        String UseFlag = (useFlag == null) ? "N" : "Y";
	        this.setUseFlag(UseFlag);
	        
	        // Admin Flag 
	        String AdminFlag = (adminFlag == null) ? "N" : "Y";
	        this.setAdminFlag(adminFlag);
	        
	    	String imageName = files != null ? files.getOriginalFilename() : "/assets/media/users/default.jpg";
	    	this.setImage(imageName);
	            
	    }

}
