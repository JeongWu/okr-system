package com.eximbay.okr.entity;

import com.eximbay.okr.constant.FlagOption;
import com.eximbay.okr.listener.AbstractAuditable;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "code_list")
@Entity
public class CodeList extends AbstractAuditable {

    @EmbeddedId
    private CodeListId codeListId;

    @Column(name = "CODE", length = 50, insertable = false, updatable = false)
    private String code;

    @Column(name = "GROUP_CODE", length = 20, insertable = false, updatable = false)
    private String groupCode;

    @Column(name = "CODE_NAME", length = 50, nullable = false)
    private String codeName;

    @Column(name = "DESCRIPTION", length = 100)
    private String description;

    @Column(name = "SORT_ORDER", length = 11, nullable = false)
    private int sortOrder;

    @Column(name = "USE_FLAG", length = 1, nullable = false)
    private String useFlag = FlagOption.Y;

}


