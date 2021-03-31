package com.eximbay.okr.api;

import com.eximbay.okr.dto.member.MemberDto;
import com.eximbay.okr.dto.okrschedulehistory.ScheduleHistoryDatatablesInput;
import com.eximbay.okr.entity.MemberHistory;
import com.eximbay.okr.service.Interface.IMemberHistoryDataService;
import com.eximbay.okr.service.Interface.IMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/member-histories")
public class MemberHistoryAPI {

    private final IMemberHistoryDataService memberHistoryService;
    private final IMemberService memberService;

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
