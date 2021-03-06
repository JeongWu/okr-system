package com.eximbay.okr.entity;

import com.eximbay.okr.constant.FlagOption;
import com.eximbay.okr.listener.AbstractAuditable;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "key_result")
@Entity
public class KeyResult extends AbstractAuditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "KEY_RESULT_SEQ", length = 11)
    private Integer keyResultSeq;

    @Column(name = "OBJECTIVE_SEQ", insertable = false, updatable = false)
    private Integer objectiveSeq;

    @ManyToOne
    @JoinColumn(name = "OBJECTIVE_SEQ", nullable = false)
    private Objective objective;

    @Column(name = "OBJECTIVE_LEVEL", length = 20, nullable = false)
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

    @Column(name = "PROPORTION", length = 11, nullable = false)
    private Integer proportion = 0;

    @Column(name = "PROGRESS", length = 11, nullable = false)
    private Integer progress = 0;

    @Column(name = "LATEST_UPDATE_DT")
    private Instant latestUpdateDt;

    @Column(name = "CLOSE_FLAG", length = 1, nullable = false)
    private String closeFlag = FlagOption.N;

    @Column(name = "CLOSE_JUSTIFICATION")
    private String closeJustification;

    @Column(name = "CLOSE_DATE", length = 8)
    private String closeDate;

    public boolean isClosed() {
        return FlagOption.isYes(this.closeFlag);
    }
}
