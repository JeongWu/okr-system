package com.eximbay.okr.service;

import com.eximbay.okr.dto.keyresulthistory.KeyResultHistoryDatatablesInput;
import com.eximbay.okr.dto.keyresulthistory.KeyResultHistoryDto;
import com.eximbay.okr.entity.KeyResultHistory;
import com.eximbay.okr.repository.KeyResultHistoryRepository;
import com.eximbay.okr.service.Interface.IKeyResultHistoryService;
import com.eximbay.okr.service.specification.KeyResultHistoryQuery;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KeyResultHistoryServiceImpl implements IKeyResultHistoryService {

    private final MapperFacade mapper;
    private final KeyResultHistoryRepository keyResultHistoryRepository;
    private final KeyResultHistoryQuery keyResultHistoryQuery;

    @Override
    public List<KeyResultHistoryDto> findAll() {
        List<KeyResultHistory> keyResultHistories = Lists.newArrayList(keyResultHistoryRepository.findAll());
        return mapper.mapAsList(keyResultHistories, KeyResultHistoryDto.class);
    }

    @Override
    public Optional<KeyResultHistoryDto> findById(Integer id) {
        Optional<KeyResultHistory> keyResultHistory = keyResultHistoryRepository.findById(id);
        return keyResultHistory.map(m -> mapper.map(m, KeyResultHistoryDto.class));
    }

    @Override
    public void remove(KeyResultHistoryDto keyResultHistoryDto) {
        KeyResultHistory keyResultHistory = mapper.map(keyResultHistoryDto, KeyResultHistory.class);
        keyResultHistoryRepository.delete(keyResultHistory);
    }

    @Override
    public KeyResultHistoryDto save(KeyResultHistoryDto keyResultHistoryDto) {
        KeyResultHistory keyResultHistory = mapper.map(keyResultHistoryDto, KeyResultHistory.class);
        keyResultHistory = keyResultHistoryRepository.save(keyResultHistory);
        return mapper.map(keyResultHistory, KeyResultHistoryDto.class);
    }

    @Override
    public DataTablesOutput<KeyResultHistory> getDataForDatatables(KeyResultHistoryDatatablesInput input) {
        DataTablesOutput<KeyResultHistory> output = input.getKeyResultSeq() != null ?
                keyResultHistoryRepository.findAll(input, keyResultHistoryQuery.findByKeyResultSeq(input.getKeyResultSeq()))
                : keyResultHistoryRepository.findAll(input);
        return output;
    }
}
