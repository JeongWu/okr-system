package com.eximbay.okr.service;

import com.eximbay.okr.dto.objective.ObjectiveDto;
import com.eximbay.okr.dto.okrchecklistgroup.OkrChecklistGroupDto;
import com.eximbay.okr.entity.Objective;
import com.eximbay.okr.entity.OkrChecklistGroup;
import com.eximbay.okr.repository.OkrCheckListGroupDetailRepository;
import com.eximbay.okr.service.Interface.IObjectiveService;
import com.eximbay.okr.service.Interface.IOkrCheckListGroupDetailService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Data
@RequiredArgsConstructor
@Service
public class OkrCheckListGroupDetailServiceImpl implements IOkrCheckListGroupDetailService {

    private final MapperFacade mapper;
    private final OkrCheckListGroupDetailRepository okrCheckListGroupDetailRepository;
    private final IObjectiveService objectiveService;

    @Override
    public List<OkrChecklistGroupDto> findAll() {
        List<OkrChecklistGroup> okrChekListGroups = okrCheckListGroupDetailRepository.findAll();
        return mapper.mapAsList(okrChekListGroups, OkrChecklistGroupDto.class);
    }

    @Override
    public Optional<OkrChecklistGroupDto> findById(Integer id) {
        Optional<OkrChecklistGroup> okrChekListGroup = okrCheckListGroupDetailRepository.findById(id);
        return okrChekListGroup.map(m -> mapper.map(m, OkrChecklistGroupDto.class));
    }

    @Override
    public void remove(OkrChecklistGroupDto okrChecklistGroupDto) {
        OkrChecklistGroup okrChekListGroup = mapper.map(okrChecklistGroupDto, OkrChecklistGroup.class);
        okrCheckListGroupDetailRepository.delete(okrChekListGroup);
    }

    @Override
    public OkrChecklistGroupDto save(OkrChecklistGroupDto okrChecklistGroupDto) {
        OkrChecklistGroup okrChekListGroup = mapper.map(okrChecklistGroupDto, OkrChecklistGroup.class);
        okrChekListGroup = okrCheckListGroupDetailRepository.save(okrChekListGroup);
        return mapper.map(okrChekListGroup, OkrChecklistGroupDto.class);
    }

    @Override
    public OkrChecklistGroupDto findLatestGroupDto(ObjectiveDto objectiveDto) {
        Objective objective = mapper.map(objectiveDto, Objective.class);
        OkrChecklistGroup okrChecklistGroup = okrCheckListGroupDetailRepository.findTop1ByObjectiveOrderByCreatedDateDesc(objective);
        return mapper.map(okrChecklistGroup, OkrChecklistGroupDto.class);
    }
}
