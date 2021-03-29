package com.eximbay.okr.service.Interface;

import com.eximbay.okr.dto.keyresult.KeyResultDto;
import com.eximbay.okr.model.keyResult.KeyResultViewOkrModel;

import java.util.List;

public interface IKeyResultService extends ISerivce<KeyResultDto, Integer> {
    List<KeyResultViewOkrModel> findByObjectiveSeqIn(List<Integer> in);
    List<KeyResultDto> findByObjectSeq(List<Integer> in);

    List<KeyResultDto> findByCloseFlagAndObjectiveSeq(String closeFlag, int objectiveSeq);

    void updateKeyResult(KeyResultDto keyResultDto);

    void addKeyResult(KeyResultDto keyResultDto);

    void updateOrAddKeyResults(List<KeyResultDto> keyResultUpdateDtos);
}
