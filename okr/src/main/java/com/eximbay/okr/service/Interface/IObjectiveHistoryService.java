package com.eximbay.okr.service.Interface;

import com.eximbay.okr.dto.objectiveHistory.ObjectiveHistoryDto;
import com.eximbay.okr.entity.ObjectiveHistory;

import java.util.List;

public interface IObjectiveHistoryService extends ISerivce<ObjectiveHistoryDto, Integer> {

    void saveAll(List<ObjectiveHistoryDto> historyDtos);
}
