package com.eximbay.okr.entity;

import com.eximbay.okr.enumeration.ScheduleType;
import com.eximbay.okr.listener.AbstractAuditable;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "okr_schedule")
@Entity
public class OkrSchedule extends AbstractAuditable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "SCHEDULE_SEQ", length = 11, nullable = false)
    private Integer scheduleSeq;

    @Enumerated(EnumType.STRING)
    @Column(name = "SCHEDULE_TYPE", length = 20, nullable = false)
    private ScheduleType scheduleType = ScheduleType.QUARTERLY;

    @Column(name = "BEGIN_DAY", length = 11, nullable = false)
    private Integer beginDay;

    @Column(name = "END_DAY", length = 11, nullable = false)
    private Integer endDay;

    @Column(name = "CLOSE_AFTER_DAYS", length = 11, nullable = false)
    private Integer closeAfterDays= 0;

    @Column(name = "REMIND_BEFORE_DAYS", length = 50)
    private String remindBeforeDays;

    @Column(name = "REMIND_TIME", length = 10)
    private String remindTime;
}
