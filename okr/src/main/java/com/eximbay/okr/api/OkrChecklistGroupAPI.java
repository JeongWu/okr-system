package com.eximbay.okr.api;

import com.eximbay.okr.dto.objective.ObjectiveDto;
import com.eximbay.okr.dto.okrchecklistgroup.ChecklistGroupDatatablesInput;
import com.eximbay.okr.entity.OkrChecklistGroup;
import com.eximbay.okr.service.Interface.IObjectiveService;
import com.eximbay.okr.service.Interface.IOkrChecklistGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/okr-checklist-groups")
public class OkrChecklistGroupAPI {

    private final IOkrChecklistGroupService okrChecklistGroupService;
    private final IObjectiveService objectiveService;

    @RequestMapping("/datatables")
    public DataTablesOutput<OkrChecklistGroup> getAll(@Valid @RequestBody(required = false) ChecklistGroupDatatablesInput input){
        ObjectiveDto dto = objectiveService.findById(1).orElseThrow(() -> new NullPointerException("Null"));
        DataTablesOutput<OkrChecklistGroup> okrChecklistGroupDtos = okrChecklistGroupService.getDataForDatatablesObjective(dto, input);
        return okrChecklistGroupDtos;
    }
    
}
