package com.eximbay.okr.service.Interface;

import com.eximbay.okr.dto.objectivehistory.ObjectiveHistoryDatatablesInput;
import com.eximbay.okr.dto.objectivehistory.ObjectiveHistoryDto;
import com.eximbay.okr.entity.ObjectiveHistory;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import java.util.List;

public interface IObjectiveHistoryService extends IService<ObjectiveHistoryDto, Integer> {

    void saveAll(List<ObjectiveHistoryDto> historyDtos);

    DataTablesOutput<ObjectiveHistory> getDataForDatatables(ObjectiveHistoryDatatablesInput input);
}
