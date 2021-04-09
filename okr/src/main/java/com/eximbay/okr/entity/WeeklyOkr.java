package com.eximbay.okr.entity;

import com.eximbay.okr.listener.AbstractAuditable;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "WEEKLY_OKR")
public class WeeklyOkr extends AbstractAuditable {

    @Id
    @Column(name = "WEEKLY_OKR_SEQ", length = 11)
    private Integer weeklyOkrSeq;

    @Column(name = "WEEKLY_SEQ", insertable = false, updatable = false)
    private Integer weeklySeq;

    @ManyToOne
    @JoinColumn(name = "WEEKLY_SEQ")
    private WeeklyPrCard weeklyPrCard;

    @Column(name = "OBJECTIVE_SEQ", insertable = false, updatable = false)
    private Integer objectiveSeq;

    @ManyToOne
    @JoinColumn(name = "OBJECTIVE_SEQ")
    private Objective objective;

    @Column(name = "KEY_RESULT_SEQ", insertable = false, updatable = false)
    private Integer keyResultSeq;

    @ManyToOne
    @JoinColumn(name = "KEY_RESULT_SEQ")
    private KeyResult keyResult;

    @Column(name = "INIT_PROGRESS", length = 11)
    private int initProgress = 0;

    @Column(name = "FINAL_PROGRESS", length = 11)
    private int finalProgress = 0;

}
