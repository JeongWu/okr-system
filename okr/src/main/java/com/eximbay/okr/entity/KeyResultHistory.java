package com.eximbay.okr.entity;

import com.eximbay.okr.constant.FlagOption;
import com.eximbay.okr.listener.AbstractAuditable;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.Instant;

@Data
@Table(name = "key_result_history")
@Entity
public class KeyResultHistory extends AbstractAuditable {

    public static final String DEFAULT_ADD_NEW_KEY_RESULT_JUSTIFICATION = "Add new key result";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "HISTORY_SEQ")
    private Integer historySeq;

    @ManyToOne
    @JoinColumn(name = "KEY_RESULT_SEQ")
    private KeyResult sourceKeyResult;

    @ManyToOne
    @JoinColumn(name = "OBJECTIVE_SEQ", nullable = false)
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

    @Column(name = "PROPORTION")
    private Integer proportion = 0;

    @Column(name = "PROGRESS", length = 11, nullable = false)
    private Integer progress = 0;

    @Column(name = "LATEST_UPDATE_DT")
    private Instant lastUpdatedDate;

    @Column(name = "CLOSE_FLAG", length = 1, nullable = false)
    private String closeFlag = FlagOption.N;

    @Column(name = "CLOSE_JUSTIFICATION")
    private String closeJustification;

    @Column(name = "CLOSE_DATE", length = 8)
    private String closeDate;

    @Column(name = "JUSTIFICATION")
    private String justification;
}