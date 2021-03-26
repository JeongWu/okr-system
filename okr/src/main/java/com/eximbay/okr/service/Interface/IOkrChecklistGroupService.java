package com.eximbay.okr.service.Interface;

import com.eximbay.okr.dto.objective.ObjectiveDto;
import com.eximbay.okr.dto.okrChecklistGroup.ChecklistGroupDatatablesInput;
import com.eximbay.okr.dto.okrChecklistGroup.OkrChecklistGroupDto;
import com.eximbay.okr.entity.OkrChecklistGroup;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

public interface IOkrChecklistGroupService extends ISerivce<OkrChecklistGroupDto, Integer> {
        DataTablesOutput<OkrChecklistGroup> getDataForDatatables( ChecklistGroupDatatablesInput input);
        DataTablesOutput<OkrChecklistGroup> getDataForDatatablesObjective(ObjectiveDto objectiveDto,
            ChecklistGroupDatatablesInput input);
    
}

