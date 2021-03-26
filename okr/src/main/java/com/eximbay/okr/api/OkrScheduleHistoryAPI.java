package com.eximbay.okr.api;

import com.eximbay.okr.dto.okrScheduleHistory.ScheduleHistoryDatatablesInput;
import com.eximbay.okr.entity.OkrScheduleHistory;
import com.eximbay.okr.service.Interface.IOkrScheduleHistoryService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Data
@AllArgsConstructor
@RestController
@RequestMapping("api/okr-schedule-histories")
public class OkrScheduleHistoryAPI {
    private final IOkrScheduleHistoryService okrScheduleHistoryService;

    @PostMapping("/datatables")
    public DataTablesOutput<OkrScheduleHistory> getAll(@Valid @RequestBody ScheduleHistoryDatatablesInput input) {
        DataTablesOutput<OkrScheduleHistory> okrScheduleHistoryDtos = okrScheduleHistoryService.getDataForDatatables(input);
        return okrScheduleHistoryDtos;
    }
}
