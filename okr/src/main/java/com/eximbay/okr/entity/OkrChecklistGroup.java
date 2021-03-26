package com.eximbay.okr.entity;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.eximbay.okr.listener.AbstractAuditable;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Table(name = "okr_checklist_group")
@Entity
public class OkrChecklistGroup{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "GROUP_SEQ", length = 11, nullable = false)
    private Integer groupSeq;
    
    @ManyToOne
    @JoinColumn(name = "OBJECTIVE_SEQ", nullable = false)
    private Objective objective;

    @Column(name = "KEY_RESULT1_SCORE", length = 11)
    private Integer keyResult1Score;

    @Column(name = "KEY_RESULT2_SCORE", length = 11)
    private Integer keyResult2Score;

    @Column(name = "KEY_RESULT3_SCORE", length = 11)
    private Integer keyResult3Score;

    @Column(name = "KEY_RESULT4_SCORE", length = 11)
    private Integer keyResult4Score;

    @Column(name = "KEY_RESULT5_SCORE", length = 11)
    private Integer keyResult5Score;

    @Column(name = "OBJECTIVE_SCORE", length = 11)
    private Integer objectiveScore;

    @Column(name = "SOURCE_OBJECTIVE", length = 20)
    private String sourceObjective;

    @CreatedBy
    @Column(name = "REG_USER_ID")
    protected String createdBy = "system";

    @CreatedDate
    @Column(name = "REG_DT")
    protected Instant createdDate = Instant.now();


    
}
