package com.eximbay.okr.service;

import com.eximbay.okr.dto.checklist.CheckListDto;
import com.eximbay.okr.entity.CheckList;
import com.eximbay.okr.repository.CheckListRepository;
import com.eximbay.okr.service.Interface.ICheckListService;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CheckListServiceImpl implements ICheckListService {

    private final MapperFacade mapper;
    private final CheckListRepository checkListRepository;

    @Override
    public List<CheckListDto> findAll() {
        List<CheckList> checklists = checkListRepository.findAll();
        return mapper.mapAsList(checklists, CheckListDto.class);
    }

    @Override
    public Optional<CheckListDto> findById(Integer id) {
        Optional<CheckList> checklist = checkListRepository.findById(id);
        return checklist.map(m -> mapper.map(m, CheckListDto.class));
    }

    @Override
    public void remove(CheckListDto checkListDto) {
        CheckList checklist = mapper.map(checkListDto, CheckList.class);
        checkListRepository.delete(checklist);
    }

    @Override
    public CheckListDto save(CheckListDto checkListDto) {
        CheckList checklist = mapper.map(checkListDto, CheckList.class);
        checklist = checkListRepository.save(checklist);
        return mapper.map(checklist, CheckListDto.class);
    }

}
