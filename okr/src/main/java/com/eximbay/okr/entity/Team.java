package com.eximbay.okr.entity;

import com.eximbay.okr.constant.FlagOption;
import com.eximbay.okr.enumeration.TeamType;
import com.eximbay.okr.listener.AbstractAuditable;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@Table(name = "team")
@Entity
@ToString(exclude = { "division", "objectives", "teamMembers"})
@EqualsAndHashCode(callSuper = true, exclude = { "division", "objectives", "teamMembers"})
public class Team extends AbstractAuditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "TEAM_SEQ", length = 11)
    private Integer teamSeq;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "DIVISION_SEQ", nullable = false)
    private Division division;

    @Column(name = "NAME", length = 50, nullable = false)
    private String name;

    @Column(name = "LOCAL_NAME", length = 50, nullable = false)
    private String localName;

    @Enumerated(EnumType.STRING)
    @Column(name = "TEAM_TYPE", length = 20, nullable = false)
    private TeamType teamType = TeamType.TEAM;

    @Column(name = "INTRODUCTION", length = 1000)
    private String introduction;

    @Column(name = "IMAGE")
    private String image;

    @Column(name = "USE_FLAG", length = 1, nullable = false)
    private String useFlag = FlagOption.N;

    @OneToMany(mappedBy = "team")
    @JsonIgnore
    
    private List<Objective> objectives;

    @OneToMany(mappedBy = "teamMemberId.team")
    @JsonIgnore
    private List<TeamMember> teamMembers;
}
