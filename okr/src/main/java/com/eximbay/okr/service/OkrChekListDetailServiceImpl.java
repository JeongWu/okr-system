package com.eximbay.okr.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.eximbay.okr.dto.checklist.OkrCheckListDetailDto;
import com.eximbay.okr.dto.okrchecklistgroup.OkrChecklistGroupDto;
import com.eximbay.okr.entity.OkrCheckListDetail;
import com.eximbay.okr.entity.OkrChecklistGroup;
import com.eximbay.okr.repository.OkrChecklistDetailRepository;
import com.eximbay.okr.repository.OkrChecklistGroupRepository;
import com.eximbay.okr.service.Interface.IOkrCheckListDetailService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ma.glasnost.orika.*;
import lombok.AllArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OkrChekListDetailServiceImpl implements IOkrCheckListDetailService {

    private final MapperFacade mapper;
    private final OkrChecklistDetailRepository checklistDetailRepository;
    private final OkrChecklistGroupRepository repository;

    @Override
    public List<OkrCheckListDetailDto> findAll() {
        List<OkrCheckListDetail> details = checklistDetailRepository.findAll();
        return mapper.mapAsList(details, OkrCheckListDetailDto.class);
    }

    @Override
    public Optional<OkrCheckListDetailDto> findById(Integer id) {
        Optional<OkrCheckListDetail> detail = checklistDetailRepository.findById(id);
        return detail.map(m-> mapper.map(m, OkrCheckListDetailDto.class));
    }

    @Override
    public void remove(OkrCheckListDetailDto okrCheckListDetailDto) {
        OkrCheckListDetail detail = mapper.map(okrCheckListDetailDto, OkrCheckListDetail.class);
        checklistDetailRepository.delete(detail);
    }

    @Override
    public OkrCheckListDetailDto save(OkrCheckListDetailDto okrCheckListDetailDto) {
        OkrCheckListDetail detail = mapper.map(okrCheckListDetailDto, OkrCheckListDetail.class);
        detail = checklistDetailRepository.save(detail);
        return mapper.map(detail, OkrCheckListDetailDto.class);
    }

    @Override
    public List<OkrCheckListDetailDto> findByOkrChecklistGroup(OkrChecklistGroupDto okrChecklistGroupDto) {
        OkrChecklistGroup groupSeq = repository.findById(okrChecklistGroupDto.getGroupSeq())
        .orElseThrow(() -> new NullPointerException("Null"));
        List<OkrCheckListDetail> details = checklistDetailRepository.findByOkrChecklistGroup(groupSeq);
        return mapper.mapAsList(details, OkrCheckListDetailDto.class);
    }

}
