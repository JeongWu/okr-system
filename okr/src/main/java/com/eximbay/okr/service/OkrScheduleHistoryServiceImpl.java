package com.eximbay.okr.service;

import com.eximbay.okr.dto.okrschedulehistory.OkrScheduleHistoryDto;
import com.eximbay.okr.dto.okrschedulehistory.ScheduleHistoryDatatablesInput;
import com.eximbay.okr.entity.OkrScheduleHistory;
import com.eximbay.okr.repository.OkrScheduleHistoryRepository;
import com.eximbay.okr.service.Interface.IOkrScheduleHistoryService;
import com.eximbay.okr.service.specification.OkrScheduleHistoryQuery;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Data
@RequiredArgsConstructor
public class OkrScheduleHistoryServiceImpl implements IOkrScheduleHistoryService {

    private final MapperFacade mapper;
    private final OkrScheduleHistoryRepository okrScheduleHistoryRepository;
    private final OkrScheduleHistoryQuery okrScheduleHistoryQuery;

    @Override
    public List<OkrScheduleHistoryDto> findAll() {
        List<OkrScheduleHistory> okrScheduleHistories = Lists.newArrayList(okrScheduleHistoryRepository.findAll());
        return mapper.mapAsList(okrScheduleHistories, OkrScheduleHistoryDto.class);
    }

    @Override
    public Optional<OkrScheduleHistoryDto> findById(Integer id) {
        Optional<OkrScheduleHistory> okrScheduleHistory = okrScheduleHistoryRepository.findById(id);
        return okrScheduleHistory.map(m -> mapper.map(m, OkrScheduleHistoryDto.class));
    }

    @Override
    public void remove(OkrScheduleHistoryDto okrScheduleHistoryDto) {
        OkrScheduleHistory okrScheduleHistory = mapper.map(okrScheduleHistoryDto, OkrScheduleHistory.class);
        okrScheduleHistoryRepository.delete(okrScheduleHistory);
    }

    @Override
    public OkrScheduleHistoryDto save(OkrScheduleHistoryDto okrScheduleHistoryDto) {
        OkrScheduleHistory okrScheduleHistory = mapper.map(okrScheduleHistoryDto, OkrScheduleHistory.class);
        okrScheduleHistory = okrScheduleHistoryRepository.save(okrScheduleHistory);
        return mapper.map(okrScheduleHistory, OkrScheduleHistoryDto.class);
    }

    @Override
    public DataTablesOutput<OkrScheduleHistory> getDataForDatatables(ScheduleHistoryDatatablesInput input) {
        DataTablesOutput<OkrScheduleHistory> output = okrScheduleHistoryRepository
                .findAll(input,
                        okrScheduleHistoryQuery.buildQueryForDatatables(input)
                );
        return output;
    }

}
