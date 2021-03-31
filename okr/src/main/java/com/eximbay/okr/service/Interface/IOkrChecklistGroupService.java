package com.eximbay.okr.service.Interface;

import com.eximbay.okr.dto.objective.ObjectiveDto;
import com.eximbay.okr.dto.okrchecklistgroup.ChecklistGroupDatatablesInput;
import com.eximbay.okr.dto.okrchecklistgroup.OkrChecklistGroupDto;
import com.eximbay.okr.entity.OkrChecklistGroup;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

public interface IOkrChecklistGroupService extends IService<OkrChecklistGroupDto, Integer> {
    DataTablesOutput<OkrChecklistGroup> getDataForDatatables(ChecklistGroupDatatablesInput input);

    DataTablesOutput<OkrChecklistGroup> getDataForDatatablesObjective(ObjectiveDto objectiveDto,
                                                                      ChecklistGroupDatatablesInput input);

}

