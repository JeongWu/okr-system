package com.eximbay.okr.entity;

import com.eximbay.okr.constant.FlagOption;
import com.eximbay.okr.listener.AbstractAuditable;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.Instant;

@Data
@Table(name = "objective")
@Entity
@ToString(exclude = { "company", "division", "team", "member"})
@EqualsAndHashCode(callSuper = true, exclude = { "company", "division", "team", "member"})
public class Objective extends AbstractAuditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "OBJECTIVE_SEQ", length = 11)
    private Integer objectiveSeq;

    @Column(name = "YEAR", length = 4, nullable = false)
    private Integer year;

    @Column(name = "QUARTER", length = 11, nullable = false)
    private Integer quarter;

    @Column(name = "BEGIN_DATE", length = 8, nullable = false)
    private String beginDate;

    @Column(name = "END_DATE", length = 8, nullable = false)
    private String endDate;

    @Column(name = "OBJECTIVE_TYPE", length = 10, nullable = false)
    private String objectiveLevel;

    // @Column(name = "OBJECTIVE_LEVEL", length = 10, nullable = false)
    // private String objectiveLevel;

    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "DIVISION_SEQ")
    // @JsonIgnore
    // private Division division;

    // @ManyToOne
    // @Column(name = "OBJECTIVE_TYPE", length = 10, nullable = false)
    // private String objectiveType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMPANY_SEQ")
    @JsonBackReference
    private Company company;

    // @ManyToOne
    // @JoinColumn(name = "DIVISION_SEQ")
    // private Division division;

    @ManyToOne
    @JoinColumn(name = "TEAM_SEQ")
    private Team team;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MEMBER_SEQ")
    private Member member;

    @Column(name = "OBJECTIVE", nullable = false)
    private String objective;

    @Column(name = "PRIORITY", length = 11, nullable = false)
    private Integer priority;

    @Column(name = "PROGRESS", length = 11, nullable = false)
    private Integer progress;

    @Column(name = "LATEST_UPDATE_DT", nullable = false)
    private Instant lastUpdateDate;

    @Column(name = "CLOSE_FLAG", length = 1, nullable = false)
    private String closeFlag = FlagOption.N;

    @Column(name = "CLOSE_JUSTIFICATION")
    private String closeJustification;

    @Column(name = "CLOSE_DATE")
    private Instant closeDate;
}
