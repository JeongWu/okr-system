package com.eximbay.okr.service.Interface;

import com.eximbay.okr.dto.keyresultcollaborator.KeyResultCollaboratorDto;

import java.util.List;

public interface IKeyResultCollaboratorService extends IService<KeyResultCollaboratorDto, Integer> {
    List<KeyResultCollaboratorDto> findByKeyResultSeqIn(List<Integer> in);
}
