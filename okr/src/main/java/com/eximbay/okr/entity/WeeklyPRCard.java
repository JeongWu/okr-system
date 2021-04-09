package com.eximbay.okr.entity;

import com.eximbay.okr.listener.AbstractAuditable;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@Entity
@Table(name = "WEEKLY_PR_CARD")
public class WeeklyPrCard extends AbstractAuditable {

    @Id
    @Column(name = "WEEKLY_SEQ", length = 11)
    private Integer weeklySeq;

    @Column(name = "YEAR", length = 11)
    private Integer year;

    @Min(1)
    @Max(52)
    @Column(name = "WEEK", length = 11)
    private Integer week;

    @Column(name = "BEGIN_DATE", length = 8)
    private String beginDate;

    @Column(name = "END_DATE", length = 8)
    private String endDate;

    @Column(name = "WEEK_END_DATE", length = 8)
    private String weekEndDate;

    @Column(name = "MEMBER_SEQ", insertable = false, updatable = false, length = 11)
    private Integer memberSeq;

    @ManyToOne
    @JoinColumn(name = "MEMBER_SEQ")
    private Member memmber;

    @Column(name = "CHALLENGE", length = 1000)
    private String challenge;

}
