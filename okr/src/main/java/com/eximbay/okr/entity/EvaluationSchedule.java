package com.eximbay.okr.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.eximbay.okr.listener.AbstractAuditable;

import lombok.Data;

@Data
@Table(name = "evaluation_schedule")
@Entity
public class EvaluationSchedule extends AbstractAuditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "SCHEDULE_SEQ", length = 11)
    private Integer scheduleSeq;

    @Column(name = "EVALUATION_TYPE", length = 20, nullable = false)
    private String evaluationType;

    @Column(name = "EVALUATION_STEP", length = 11, nullable = false)
    private int evaluationStep;

    @Column(name = "SELF_REVIEW_BEGIN_DATE", length = 8, nullable = false)
    private String selfReviewBeginDate;

    @Column(name = "SELF_REVIEW_END_DATE", length = 8, nullable = false)
    private String selfReviewEndDate;

    @Column(name = "FIRST_REVIEW_BEGIN_DATE", length = 8, nullable = false)
    private String firstReviewBeginDate;

    @Column(name = "FIRST_REVIEW_END_DATE", length = 8, nullable = false)
    private String firstReviewEndDate;

    @Column(name = "SECOND_REVIEW_BEGIN_DATE", length = 8, nullable = false)
    private String secondReviewBeginDate;

    @Column(name = "SECOND_REVIEW_END_DATE", length = 8, nullable = false)
    private String secondReviewEndDate;

    @Column(name = "REVIEW_BEGIN_DATE", length = 8, nullable = false)
    private String reviewBeginDate;

    @Column(name = "REVIEW_END_DATE", length = 8, nullable = false)
    private String reviewEndDate;
}
