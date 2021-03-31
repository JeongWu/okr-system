package com.eximbay.okr.api;

import com.eximbay.okr.dto.keyresulthistory.KeyResultHistoryDatatablesInput;
import com.eximbay.okr.dto.keyresulthistory.KeyResultHistoryDto;
import com.eximbay.okr.dto.objectivehistory.ObjectiveHistoryDto;
import com.eximbay.okr.entity.KeyResultHistory;
import com.eximbay.okr.service.Interface.IKeyResultHistoryService;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/key-result-histories")
public class KeyResultHistoryAPI {

    private final MapperFacade mapper;
    private final IKeyResultHistoryService keyResultHistoryService;

    @PostMapping("/datatables")
    public DataTablesOutput<KeyResultHistoryDto> getAll(@RequestBody KeyResultHistoryDatatablesInput input) {
        DataTablesOutput<KeyResultHistory> histories = keyResultHistoryService.getDataForDatatables(input);
        DataTablesOutput<KeyResultHistoryDto> response = new DataTablesOutput<>();
        mapper.map(histories, response);
        response.setData(mapper.mapAsList(histories.getData(), KeyResultHistoryDto.class));
        return response;
    }
}
