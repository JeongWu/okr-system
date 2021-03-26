package com.eximbay.okr.entity;

import com.eximbay.okr.listener.AbstractAuditable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Data
@Table(name = "division_history")
@Entity
@ToString(exclude = { "division", "company"})
@EqualsAndHashCode(callSuper = true, exclude =  {"division", "company"})
public class DivisionHistory extends AbstractAuditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "HISTORY_SEQ", length = 11)
    private Integer historySeq;

    @ManyToOne
    @JoinColumn(name = "DIVISION_SEQ", nullable = false)
    private Division division;

    @ManyToOne
    @JoinColumn(name = "COMPANY_SEQ")
    private Company company;

    @Column(name = "NAME", length = 50)
    private String name;

    @Column(name = "LOCAL_NAME", length = 50)
    private String localName;

    @Column(name = "USE_FLAG", length = 1)
    private String useFlag;

    @Column(name = "JUSTIFICATION", nullable = false)
    private String justification;
}
