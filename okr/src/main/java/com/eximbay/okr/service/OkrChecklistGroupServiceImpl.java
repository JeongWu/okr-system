package com.eximbay.okr.service;

import java.util.List;
import java.util.Optional;

import com.eximbay.okr.dto.objective.ObjectiveDto;
import com.eximbay.okr.dto.okrChecklistGroup.ChecklistGroupDatatablesInput;
import com.eximbay.okr.dto.okrChecklistGroup.OkrChecklistGroupDto;
import com.eximbay.okr.entity.Objective;
import com.eximbay.okr.entity.OkrChecklistGroup;
import com.eximbay.okr.repository.OkrChecklistGroupRepository;
import com.eximbay.okr.service.Interface.IOkrChecklistGroupService;
import com.eximbay.okr.service.specification.OkrChecklistGroupQuery;
import com.google.common.collect.Lists;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Data;
import ma.glasnost.orika.MapperFacade;

@Data
@AllArgsConstructor
@Service
public class OkrChecklistGroupServiceImpl implements IOkrChecklistGroupService {

    private final OkrChecklistGroupRepository okrChecklistGroupRepository;
    private final OkrChecklistGroupQuery okrChecklistGroupQuery;
    private final MapperFacade mapper;

    @Override
    public List<OkrChecklistGroupDto> findAll() {
        List<OkrChecklistGroup> okrScheduleHistories = Lists.newArrayList(okrChecklistGroupRepository.findAll());
        return mapper.mapAsList(okrScheduleHistories, OkrChecklistGroupDto.class);
    }

    @Override
    public Optional<OkrChecklistGroupDto> findById(Integer id) {
        Optional<OkrChecklistGroup> okrChecklistGroup = okrChecklistGroupRepository.findById(id);
        return okrChecklistGroup.map(m-> mapper.map(m, OkrChecklistGroupDto.class));
    }

    @Override
    public void remove(OkrChecklistGroupDto okrChecklistGroupDto) {
        OkrChecklistGroup okrChecklistGroup = mapper.map(okrChecklistGroupDto, OkrChecklistGroup.class);
        okrChecklistGroupRepository.delete(okrChecklistGroup);
    }

    @Override
    public OkrChecklistGroupDto save(OkrChecklistGroupDto okrChecklistGroupDto) {
        OkrChecklistGroup okrChecklistGroup = mapper.map(okrChecklistGroupDto, OkrChecklistGroup.class);
        okrChecklistGroup = okrChecklistGroupRepository.save(okrChecklistGroup);
        return mapper.map(okrChecklistGroup, OkrChecklistGroupDto.class);
    }

    @Override
    public DataTablesOutput<OkrChecklistGroup> getDataForDatatables( ChecklistGroupDatatablesInput input) {
        // Objective objective = mapper.map(objectiveDto, Objective.class);
        DataTablesOutput<OkrChecklistGroup> output = okrChecklistGroupRepository

                                                        .findAll(input,
                                                                okrChecklistGroupQuery.buildQueryForDatatables( input)
                                                        );
        return output;
    }

    @Override
    public DataTablesOutput<OkrChecklistGroup> getDataForDatatablesObjective(ObjectiveDto objectiveDto,
            ChecklistGroupDatatablesInput input) {
                Objective objective = mapper.map(objectiveDto, Objective.class);
                DataTablesOutput<OkrChecklistGroup> output = okrChecklistGroupRepository.findAll(input, 
                okrChecklistGroupQuery.buildQueryForDatatablesObjective(objective, input));
                return output;
    }


    
}
