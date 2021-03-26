package com.eximbay.okr.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.eximbay.okr.listener.AbstractAuditable;

import lombok.Data;

@Data
@Table(name = "assessment_criteria")
@Entity
public class AssessmentCriteria extends AbstractAuditable {

    @Column(name = "GROUP_CODE", length = 20, nullable = false)
    private String groupCode;
    
    @Id
    @Column(name = "CODE", length = 20, nullable = false)
    private String code;

    @Column(name = "GROUP_NAME", length = 50, nullable = false)
    private String groupName;

    @Column(name = "CODE_NAME", length = 50, nullable = false)
    private String codeName;

    @Column(name = "CODE_VALUE", length = 11, nullable = false)
    private int codeValue;

    @Column(name = "BEGIN_RANGE", length = 11, nullable = false)
    private int beginRange;

    @Column(name = "END_RANGE", length = 11, nullable = false)
    private int endRange;

    @Column(name = "DESCRIPTION", length = 100, nullable = false)
    private String discription;   
}
