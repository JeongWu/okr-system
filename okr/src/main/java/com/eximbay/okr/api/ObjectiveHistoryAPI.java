package com.eximbay.okr.api;

import com.eximbay.okr.dto.objectivehistory.ObjectiveHistoryDatatablesInput;
import com.eximbay.okr.dto.objectivehistory.ObjectiveHistoryDto;
import com.eximbay.okr.entity.ObjectiveHistory;
import com.eximbay.okr.service.Interface.IObjectiveHistoryService;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/objective-histories")
public class ObjectiveHistoryAPI {

    private final MapperFacade mapper;
    private final IObjectiveHistoryService objectiveHistoryService;

    @PostMapping("/datatables")
    public DataTablesOutput<ObjectiveHistoryDto> getAll(@RequestBody ObjectiveHistoryDatatablesInput input) {
        DataTablesOutput<ObjectiveHistory> histories = objectiveHistoryService.getDataForDatatables(input);
        DataTablesOutput<ObjectiveHistoryDto> response = new DataTablesOutput<>();
        mapper.map(histories, response);
        response.setData(mapper.mapAsList(histories.getData(), ObjectiveHistoryDto.class));
        return response;
    }
}
