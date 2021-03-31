package com.eximbay.okr.dto.keyresultcollaborator;

import com.eximbay.okr.dto.member.MemberDto;
import com.eximbay.okr.dto.keyresult.KeyResultDto;
import com.eximbay.okr.listener.AbstractAuditableDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class KeyResultCollaboratorDto extends AbstractAuditableDto {

    private Integer cbSeq;
    private KeyResultDto keyResult;
    private KeyResultDto relatedKeyResult;
    private MemberDto collaborator;
    private String collaboration;
    private String useFlag;
}
