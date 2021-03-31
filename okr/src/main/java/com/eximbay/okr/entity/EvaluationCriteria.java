package com.eximbay.okr.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.eximbay.okr.entity.id.EvaluationCriteriaId;
import com.eximbay.okr.listener.AbstractAuditable;

import lombok.Data;

@Data
@Table(name = "evaluation_criteria")
@Entity
@IdClass(EvaluationCriteriaId.class)
public class EvaluationCriteria extends AbstractAuditable {

    @Id
    @Column(name = "GROUP_CODE", length = 30, nullable = false)
    private String groupCode;
    
    @Id
    @Column(name = "CODE", length = 20, nullable = false)
    private String code;

    @Column(name = "GROUP_NAME", length = 50, nullable = false)
    private String groupName;

    @Column(name = "CODE_NAME", length = 50, nullable = false)
    private String codeName;

    @Column(name = "CODE_VALUE", length = 11, nullable = false)
    private BigDecimal codeValue;
    
    @Column(name = "BEGIN_RANGE", length = 11, nullable = false)
    private BigDecimal beginRange;

    @Column(name = "END_RANGE", length = 11, nullable = false)
    private BigDecimal endRange;

    @Column(name = "DESCRIPTION", length = 100, nullable = false)
    private String discription;   
}
