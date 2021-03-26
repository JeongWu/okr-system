package com.eximbay.okr.entity;

import com.eximbay.okr.listener.AbstractAuditable;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "okr_schedule_history")
@Entity
public class OkrScheduleHistory extends AbstractAuditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "HISTORY_SEQ", length = 11, nullable = false)
    private Integer historySeq;

    @ManyToOne
    @JoinColumn(name = "SCHEDULE_SEQ", nullable = false)
    private OkrSchedule okrSchedule;

    @Column(name = "SCHEDULE_TYPE", length = 20)
    private String scheduleType;

    @Column(name = "BEGIN_DAY", length = 11)
    private Integer beginDay;

    @Column(name = "END_DAY", length = 11)
    private Integer endDay;

    @Column(name = "CLOSE_AFTER_DAYS", length = 11)
    private Integer closeAfterDays= 0;

    @Column(name = "REMIND_BEFORE_DAYS", length = 50)
    private String remindBeforeDays;

    @Column(name = "REMIND_TIME", length = 10)
    private String remindTime;

    @Column(name = "JUSTIFICATION", nullable = false)
    private String justification;
}
