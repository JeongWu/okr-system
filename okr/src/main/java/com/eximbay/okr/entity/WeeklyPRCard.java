package com.eximbay.okr.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.eximbay.okr.listener.AbstractAuditable;

import lombok.Data;

@Data
@Table(name = "weekly_pr_card")
@Entity
public class WeeklyPRCard extends AbstractAuditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "WEEKLY_SEQ", length = 11)
    private Integer weeklySeq;
    
    @Column(name = "YEAR", length = 11, nullable = false)
    private Integer year;

    @Column(name = "WEEK", length = 11, nullable = false)
    private Integer week;

    @Column(name = "BEGIN_DATE", length = 8, nullable = false)
    private String beginDate;

    @Column(name = "END_DATE", length = 8, nullable = false)
    private String endDate;
    
    @Column(name = "WEEK_END_DATE", length = 8, nullable = false)
    private String weekEndDate;
    
    @ManyToOne
    @JoinColumn(name = "MEMBER_SEQ", nullable = false)
    private Member member;

    @Column(name = "CHALLENGE", length = 1000)
    private String chanllenge;
    
}