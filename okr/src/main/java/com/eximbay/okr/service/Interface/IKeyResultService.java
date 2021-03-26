package com.eximbay.okr.service.Interface;

import com.eximbay.okr.dto.keyResult.KeyResultDto;
import com.eximbay.okr.dto.objective.ObjectiveDto;
import com.eximbay.okr.model.keyResult.KeyResultViewOkrModel;

import java.util.List;

public interface IKeyResultService extends ISerivce<KeyResultDto, Integer> {
    List<KeyResultViewOkrModel> findByObjectiveSeqIn(List<Integer> in);
    List<KeyResultDto> findByObjectSeq(List<Integer> in);
}
