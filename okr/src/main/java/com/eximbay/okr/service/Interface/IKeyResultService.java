package com.eximbay.okr.service.Interface;

import com.eximbay.okr.dto.keyResult.KeyResultDto;
import com.eximbay.okr.model.keyResult.KeyResultCompanyOkrModel;

import java.util.List;

public interface IKeyResultService extends ISerivce<KeyResultDto, Integer> {
    List<KeyResultCompanyOkrModel> findByObjectiveSeqIn(List<Integer> in);
}
