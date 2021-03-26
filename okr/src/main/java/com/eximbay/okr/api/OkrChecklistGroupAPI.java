package com.eximbay.okr.api;

import javax.validation.Valid;

import com.eximbay.okr.dto.objective.ObjectiveDto;
import com.eximbay.okr.dto.okrChecklistGroup.ChecklistGroupDatatablesInput;
import com.eximbay.okr.entity.OkrChecklistGroup;
import com.eximbay.okr.service.ObjectiveServiceImpl;
import com.eximbay.okr.service.Interface.IOkrChecklistGroupService;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@RestController
@RequestMapping("api/okr-checklist-groups")
public class OkrChecklistGroupAPI {

    private final IOkrChecklistGroupService okrChecklistGroupService;
    private final ObjectiveServiceImpl service;

    @RequestMapping("/datatables")
    public DataTablesOutput<OkrChecklistGroup> getAll(@Valid @RequestBody(required = false) ChecklistGroupDatatablesInput input){
        ObjectiveDto dto = service.findById(1).orElseThrow(() -> new NullPointerException("Null"));
        DataTablesOutput<OkrChecklistGroup> okrChecklistGroupDtos = okrChecklistGroupService.getDataForDatatablesObjective(dto, input);
        return okrChecklistGroupDtos;
    }
    
}
