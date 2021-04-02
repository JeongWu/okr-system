package com.eximbay.okr.entity;

import com.eximbay.okr.constant.FlagOption;
import com.eximbay.okr.listener.AbstractAuditable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "code_group")
@Entity
public class CodeGroup extends AbstractAuditable {

    @Id
    @Column(name = "GROUP_CODE", length = 20, nullable = false)
    private String groupCode;

    @Column(name = "GROUP_CODE_NAME", length = 50, nullable = false)
    private String groupCodeName;

    @Column(name = "CODE_SIZE", length = 11, nullable = false)
    private int codeSize;

    @Column(name = "USE_FLAG", length = 1, nullable = false)
    private String useFlag = FlagOption.Y;

    @OneToMany(mappedBy = "codeListId.groupCode", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<CodeList> codeLists;

}
