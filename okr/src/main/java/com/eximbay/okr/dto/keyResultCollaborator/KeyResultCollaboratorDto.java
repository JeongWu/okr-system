package com.eximbay.okr.dto.keyResultCollaborator;

import com.eximbay.okr.dto.MemberDto;
import com.eximbay.okr.dto.keyResult.KeyResultDto;
import lombok.Data;

@Data
public class KeyResultCollaboratorDto {

    private Integer cbSeq;
    private KeyResultDto keyResult;
    private KeyResultDto relatedKeyResult;
    private MemberDto collaborator;
    private String collaboration;
    private String useFlag;
}
