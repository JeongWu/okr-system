package com.eximbay.okr.entity;

import com.eximbay.okr.listener.AbstractAuditable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Data
@Table(name = "team_history")
@Entity
@ToString(exclude = { "team", "division"})
@EqualsAndHashCode(callSuper = true, exclude = { "team", "division"})
public class TeamHistory extends AbstractAuditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "HISTORY_SEQ", length = 11)
    private Integer historySeq;

    @ManyToOne
    @JoinColumn(name = "TEAM_SEQ", nullable = false)
    private Team team;

    @ManyToOne
    @JoinColumn(name = "DIVISION_SEQ")
    private Division division;

    @Column(name = "NAME", length = 50)
    private String name;

    @Column(name = "LOCAL_NAME", length = 50)
    private String localName;

    @Column(name = "TEAM_TYPE", length = 20)
    private String teamType;

    @Column(name = "INTRODUCTION", length = 1000)
    private String introduction;

    @Column(name = "IMAGE")
    private String image;

    @Column(name = "USE_FLAG", length = 1)
    private String useFlag;

    @Column(name = "JUSTIFICATION", nullable = false)
    private String justification;
}
