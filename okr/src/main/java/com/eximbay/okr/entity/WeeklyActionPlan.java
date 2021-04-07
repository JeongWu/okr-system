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

import lombok.Data;

@Data
@Table(name = "weekly_action_plan")
@Entity
public class WeeklyActionPlan extends AbstractAuditable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "WEEKLY_PR_SEQ", length = 11)
    private Integer weeklyPRSeq;

    @ManyToOne
    @JoinColumn(name = "WEEKLY_SEQ", nullable = false)
    private WeeklyPRCard weeklyPRCard;
    
    @Column(name = "ACTION_PLAN", length = 1000, nullable = false)
    private String actionPlan;

    @Column(name = "REVIEW", length = 1000)
    private String review;

    @Column(name = "REVIEW_DT")
    protected Instant reviewDate;
}
