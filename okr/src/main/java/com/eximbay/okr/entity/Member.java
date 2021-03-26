package com.eximbay.okr.entity;

import com.eximbay.okr.constant.CareerLevel;
import com.eximbay.okr.constant.FlagOption;
import com.eximbay.okr.constant.MemberLevel;
import com.eximbay.okr.listener.AbstractAuditable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "member")
@Entity
public class Member extends AbstractAuditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MEMBER_SEQ", length = 11)
    private Integer memberSeq;

    @Column(name = "NAME", length = 50, nullable = false)
    private String name;

    @Column(name = "LOCAL_NAME", length = 50, nullable = false)
    private String localName;

    @Column(name = "MEMBER_ID", length = 50, nullable = false)
    private String memberId;

    @Column(name = "EMAIL", length = 100, nullable = false)
    private String email;

    @Column(name = "CONTACT_PHONE", length = 20)
    private String contactPhone;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "INTRODUCTION", length = 1000)
    private String introduction;

    @Column(name = "IMAGE")
    private String image;

    @Column(name = "POSITION", length = 20)
    private String position;

    @Column(name = "LEVEL", length = 11, nullable = false)
    private int level = MemberLevel.One;

    @Column(name = "JOINING_DATE", length = 8, nullable = false)
    private String joiningDate;

    @Column(name = "CAREER", length = 11, nullable = false)
    private int career = CareerLevel.Zero;

    @Column(name = "RETIREMENT_DATE", length = 8)
    private String retirementDate;

    @Column(name = "PASSWORD_MOD_DT", nullable = false)
    private Instant lastPasswordChange;

    @Column(name = "PASSWORD_TEMP_FLAG",length = 1, nullable = false)
    private String passwordTempFlag = FlagOption.N;

    @Column(name = "PASSWORD_ERROR_COUNT", length = 11, nullable = false)
    private int passwordErrorCount = 0;

    @Column(name = "LATEST_LOGIN_DT")
    private Instant lassLoginDate;

    @Column(name = "ADMIN_FLAG",length = 1, nullable = false)
    private String adminFlag = FlagOption.N;

    @Column(name = "ADMIN_ACCESS_IP", length = 15)
    private String adminAccessIp;

    @Column(name = "USE_FLAG", length = 1, nullable = false)
    private String useFlag = FlagOption.N;

    @OneToMany(mappedBy = "teamMemberId.member")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<TeamMember> teamMembers;
}

