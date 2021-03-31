package com.eximbay.okr.entity;

import com.eximbay.okr.listener.AbstractAuditable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "key_result_history")
@Entity
public class KeyResultHistory extends AbstractAuditable {

    public static final String DEFAULT_ADD_NEW_KEY_RESULT_JUSTIFICATION = "Add new key result";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "HISTORY_SEQ", length = 11)
    private Integer historySeq;

    @ManyToOne
    @JoinColumn(name = "KEY_RESULT_SEQ", nullable = false)
    private KeyResult keyResultObject;

    @ManyToOne
    @JoinColumn(name = "OBJECTIVE_SEQ")
    private Objective objective;

    @Column(name = "OBJECTIVE_LEVEL", length = 20)
    private String objectiveLevel;

    @Column(name = "KEY_RESULT")
    private String keyResult;

    @Column(name = "BEGIN_DATE", length = 8)
    private String beginDate;

    @Column(name = "END_DATE", length = 8)
    private String endDate;

    @Column(name = "TASK_TYPE", length = 20)
    private String taskType;

    @Column(name = "TASK_METRIC", length = 20)
    private String taskMetric;

    @Column(name = "TASK_INDICATOR", length = 20)
    private String taskIndicator;

    @Column(name = "PROPORTION", length = 11)
    private Integer proportion;

    @Column(name = "PROGRESS", length = 11)
    private Integer progress;

    @Column(name = "LATEST_UPDATE_DT")
    private Instant latestUpdateDt;

    @Column(name = "CLOSE_FLAG", length = 1)
    private String closeFlag;

    @Column(name = "CLOSE_JUSTIFICATION")
    private String closeJustification;

    @Column(name = "CLOSE_DATE", length = 8)
    private String closeDate;

    @Column(name = "JUSTIFICATION", nullable = false)
    private String justification;
}
