package com.eximbay.okr.entity;

import com.eximbay.okr.constant.FlagOption;
import com.eximbay.okr.listener.AbstractAuditable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "code_list")
@Entity
public class CodeList extends AbstractAuditable {

    @EmbeddedId
    private CodeListId codeListId;

    @Column(name = "CODE_NAME", length = 50, nullable = false)
    private String codeName;

    @Column(name = "DESCRIPTION", length = 100)
    private String description;

    @Column(name = "SORT_ORDER", length = 11, nullable = false)
    private int sortOrder;

    @Column(name = "USE_FLAG", length = 1, nullable = false)
    private String useFlag = FlagOption.Y;

}


