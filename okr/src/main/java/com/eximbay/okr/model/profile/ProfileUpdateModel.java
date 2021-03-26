package com.eximbay.okr.model.profile;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ProfileUpdateModel {

    private Integer memberSeq;

    private String name;

    private String localName;

    private String email;

    private String contactPhone;

    private String introduction;

    private String image;

    private MultipartFile imageFile;

    private String position;

    private int level;

    private String joiningDate;

}
