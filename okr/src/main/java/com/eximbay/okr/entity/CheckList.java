package com.eximbay.okr.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.eximbay.okr.constant.FlagOption;
import com.eximbay.okr.enumeration.CheckListType;
import com.eximbay.okr.listener.AbstractAuditable;

import lombok.Data;

@Data
@Table(name = "okr_checklist")
@Entity
public class CheckList extends AbstractAuditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CHECKLIST_SEQ", length = 11)
    private Integer checkListSeq;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE", length = 20, nullable = false)
    private CheckListType type = CheckListType.OBJECTIVE;

    @Column(name = "QUESTION", length = 255, nullable = false )
    private String question;

    @Column(name = "USE_FLAG", length = 1, nullable = false)
    private String useFlag = FlagOption.Y;
  
}
