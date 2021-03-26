package com.eximbay.okr.service.Interface;

import com.eximbay.okr.dto.keyResultCollaborator.KeyResultCollaboratorDto;

import java.util.List;

public interface IKeyResultCollaboratorService extends ISerivce<KeyResultCollaboratorDto, Integer> {
    List<KeyResultCollaboratorDto> findByKeyResultSeqIn(List<Integer> in);
}
