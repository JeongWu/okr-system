package com.eximbay.okr.entity;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import lombok.EqualsAndHashCode;
import lombok.Data;

@Data
@Table(name = "okr_checklist_detail")
@Entity
public class OkrCheckListDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "DETAIL_SEQ", length = 11)
    private Integer detailSeq;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "GROUP_SEQ")
    private OkrChecklistGroup okrChecklistGroup;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CHECKLIST_SEQ")
    private CheckList checkList;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "KEY_RESULT_SEQ")
    private KeyResult keyResult;

    @Column(name = "SOURCE_KEY_RESULT", length = 255, nullable = false)
    private String sourceKeyResult;

    @Column(name = "QUESTION", length = 255, nullable = false)
    private String question;

    @Column(name = "ANSWER_CODE", length = 20, nullable = false)
    private String answerCode;

    @CreatedBy
    @Column(name = "REG_USER_ID")
    protected String createdBy = "system";

    @CreatedDate
    @Column(name = "REG_DT")
    protected Instant createdDate = Instant.now();   
    
}
