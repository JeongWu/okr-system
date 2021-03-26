package com.eximbay.okr.service;

import java.util.List;
import java.util.Optional;

import com.eximbay.okr.dto.okrChecklistGroup.OkrChecklistGroupDto;
import com.eximbay.okr.entity.OkrChecklistGroup;
import com.eximbay.okr.repository.OkrCheckListGroupDetailRepository;
import com.eximbay.okr.service.Interface.IOkrCheckListGroupDetailService;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Data;
import ma.glasnost.orika.MapperFacade;

@Data
@AllArgsConstructor
@Service
public class OkrCheckListGroupDetailServiceImpl implements IOkrCheckListGroupDetailService {

    private final OkrCheckListGroupDetailRepository okrCheckListGroupDetailRepository;
    private final MapperFacade mapper;

    @Override
    public List<OkrChecklistGroupDto> findAll() {
        List<OkrChecklistGroup> okrChekListGroups = okrCheckListGroupDetailRepository.findAll();
        return mapper.mapAsList(okrChekListGroups, OkrChecklistGroupDto.class);
    }

    @Override
    public Optional<OkrChecklistGroupDto> findById(Integer id) {
        Optional<OkrChecklistGroup> okrChekListGroup = okrCheckListGroupDetailRepository.findById(id);
        return okrChekListGroup.map(m-> mapper.map(m, OkrChecklistGroupDto.class));
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
    
}
