package com.eximbay.okr.entity;

import com.eximbay.okr.constant.FlagOption;
import com.eximbay.okr.listener.AbstractAuditable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "company")
@Entity
public class Company extends AbstractAuditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "COMPANY_SEQ", length = 11)
    private Integer companySeq;

    @Column(name = "NAME", length = 50, nullable = false)
    private String name;

    @Column(name = "LOCAL_NAME", length = 50, nullable = false)
    private String localName;

    @Column(name = "LOGO")
    private String logo;

    @Column(name = "DOMAIN", length = 50)
    private String domain;

    @Column(name = "GOOGLE_SIGNIN", length = 1, nullable = false)
    private String googleSignIn = FlagOption.N;

    @Column(name = "GOOGLE_CLIENT_ID")
    private String googleClientId;

    @Column(name = "GOOGLE_CLIENT_SECRETKEY")
    private String googleClientSecretKey;

    @OneToMany(mappedBy = "company")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Division> divisions;
}
