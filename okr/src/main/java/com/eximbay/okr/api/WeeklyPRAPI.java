package com.eximbay.okr.api;

import javax.validation.Valid;

import com.eximbay.okr.dto.weeklypr.MemberDatatablesInput;
import com.eximbay.okr.entity.WeeklyPRCard;
import com.eximbay.okr.model.weeklypr.YearnAndWeekModel;
import com.eximbay.okr.service.Interface.IWeeklyPRService;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/weekly-prs")
public class WeeklyPRAPI {


    public final IWeeklyPRService weeklyPRService;


    @PostMapping("/datatables")
    public DataTablesOutput<WeeklyPRCard> getAll(@Valid @RequestBody MemberDatatablesInput input) {
        DataTablesOutput<WeeklyPRCard> weeklyPRCards = weeklyPRService.getDataForDatatables(input);
        return weeklyPRCards;
    }

    @PostMapping("/year")
    public YearnAndWeekModel getYearAndWeek(String year) {
        YearnAndWeekModel selectModels = weeklyPRService.getWeekByYear(Integer.parseInt(year));
        return selectModels;
    }
    
    
}
