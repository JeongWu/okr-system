package com.eximbay.okr.api;

import javax.validation.Valid;

import com.eximbay.okr.dto.MemberDto;
import com.eximbay.okr.dto.okrScheduleHistory.ScheduleHistoryDatatablesInput;
import com.eximbay.okr.entity.Member;
import com.eximbay.okr.entity.MemberHistory;
import com.eximbay.okr.service.MemberHistoryDataServiceImpl;
import com.eximbay.okr.service.MemberServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("api/memberhistory")
public class MemberHistoryAPI {
    private final MemberHistoryDataServiceImpl memberHistoryService;
    private final MemberServiceImpl memberService;

    @PostMapping("/datatables/{memberSeq}")
    public DataTablesOutput<MemberHistory> getAlla(
            @Valid @RequestBody(required = false) ScheduleHistoryDatatablesInput input,
            @PathVariable String memberSeq) {
        System.out.println(input);
        MemberDto dto = memberService.findById(Integer.parseInt(memberSeq))
                .orElseThrow(() -> new NullPointerException("Null"));
        DataTablesOutput<MemberHistory> data = memberHistoryService.getDataForDatatablesMember(dto, input);
        return data;
    }
}
