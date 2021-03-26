package com.eximbay.okr.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.eximbay.okr.listener.AbstractAuditable;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_SEQ", nullable = false)
    private Team team;

    @ManyToOne(fetch = FetchType.EAGER)
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
