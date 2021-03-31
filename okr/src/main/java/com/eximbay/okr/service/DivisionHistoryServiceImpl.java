package com.eximbay.okr.service;

import com.eximbay.okr.dto.divisionhistory.DivisionHistoryDto;
import com.eximbay.okr.entity.DivisionHistory;
import com.eximbay.okr.repository.DivisionHistoryRepository;
import com.eximbay.okr.service.Interface.IDivisionHistoryService;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DivisionHistoryServiceImpl implements IDivisionHistoryService {

    private final MapperFacade mapper;
    private final DivisionHistoryRepository divisionHistoryRepository;

    @Override
    public List<DivisionHistoryDto> findAll() {
        List<DivisionHistory> divisionHistories = divisionHistoryRepository.findAll();
        return mapper.mapAsList(divisionHistories, DivisionHistoryDto.class);
    }

    @Override
    public Optional<DivisionHistoryDto> findById(Integer id) {
        Optional<DivisionHistory> divisionHistory = divisionHistoryRepository.findById(id);
        return divisionHistory.map(m -> mapper.map(m, DivisionHistoryDto.class));
    }

    @Override
    public void remove(DivisionHistoryDto divisionHistoryDto) {
        DivisionHistory divisionHistory = mapper.map(divisionHistoryDto, DivisionHistory.class);
        divisionHistoryRepository.delete(divisionHistory);
    }

    @Override
    public DivisionHistoryDto save(DivisionHistoryDto divisionHistoryDto) {
        DivisionHistory divisionHistory = mapper.map(divisionHistoryDto, DivisionHistory.class);
        divisionHistory = divisionHistoryRepository.save(divisionHistory);
        return mapper.map(divisionHistory, DivisionHistoryDto.class);
    }

}
