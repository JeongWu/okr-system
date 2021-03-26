package com.eximbay.okr.entity;

import com.eximbay.okr.constant.FlagOption;
import com.eximbay.okr.listener.AbstractAuditable;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.Set;


@Data
@Table(name = "division")
@Entity
@ToString(exclude = { "company", "teams", "divisionMembers"})
@EqualsAndHashCode(callSuper = true, exclude = { "company", "teams", "divisionMembers"})
public class Division extends AbstractAuditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "DIVISION_SEQ", length = 11)
    private Integer divisionSeq;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "COMPANY_SEQ", nullable = false)
    private Company company;

    @Column(name = "NAME", length = 50, nullable = false)
    private String name;

    @Column(name = "LOCAL_NAME", length = 50, nullable = false)
    private String localName;

    @Column(name = "USE_FLAG", length = 1, nullable = false)
    private String useFlag = FlagOption.Y;

    @OneToMany(mappedBy = "division")
    @JsonIgnore
    private List<Team> teams;

    @OneToMany(mappedBy = "divisionMemberId.division")
    @JsonIgnore
    private List<DivisionMember> divisionMembers;
}
