package com.eximbay.okr.entity;

import com.eximbay.okr.constant.FlagOption;
import com.eximbay.okr.listener.AbstractAuditable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Data
@Table(name = "objective")
@Entity
@ToString(exclude = {"company", "division", "team", "member"})
@EqualsAndHashCode(callSuper = true, exclude = {"company", "division", "team", "member"})
public class Objective extends AbstractAuditable {

    public static final String OBJECTIVE_TYPE_COMPANY = "COMPANY";
    public static final String OBJECTIVE_TYPE_DIVISON = "DIVISION";
    public static final String OBJECTIVE_TYPE_TEAM = "TEAM";
    public static final String OBJECTIVE_TYPE_MEMBER = "MEMBER";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "OBJECTIVE_SEQ", length = 11)
    private Integer objectiveSeq;

    @Column(name = "YEAR", length = 4, nullable = false)
    private int year;

    @Column(name = "QUARTER", length = 11, nullable = false)
    private int quarter;

    @Column(name = "BEGIN_DATE", length = 8, nullable = false)
    private String beginDate;

    @Column(name = "END_DATE", length = 8, nullable = false)
    private String endDate;

    @Column(name = "OBJECTIVE_TYPE", length = 10, nullable = false)
    private String objectiveType;

    @ManyToOne
    @JoinColumn(name = "COMPANY_SEQ")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "TEAM_SEQ")
    private Team team;

    @ManyToOne
    @JoinColumn(name = "MEMBER_SEQ")
    private Member member;

    @Column(name = "OBJECTIVE", nullable = false)
    private String objective;

    @Column(name = "PRIORITY", length = 11, nullable = false)
    private int priority;

    @Column(name = "PROGRESS", length = 11, nullable = false)
    private int progress = 0;

    @Column(name = "CLOSE_FLAG", length = 1, nullable = false)
    private String closeFlag = FlagOption.N;

    @Column(name = "CLOSE_JUSTIFICATION")
    private String closeJustification;

    @Column(name = "CLOSE_DATE")
    private Instant closeDate;

    @Column(name = "LIKES", length = 11, nullable = false)
    private Integer likes = 0;

    @OneToMany(mappedBy = "objective")
    private List<KeyResult> keyResults;
}
