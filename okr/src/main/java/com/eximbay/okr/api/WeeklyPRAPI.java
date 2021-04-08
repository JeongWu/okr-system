package com.eximbay.okr.api;

import java.util.List;

import javax.validation.Valid;

import com.eximbay.okr.dto.weekly.MemberDatatablesInput;
import com.eximbay.okr.dto.weekly.TestData;
import com.eximbay.okr.dto.weekly.WeeklyPRCardwithMembersDto;
import com.eximbay.okr.entity.WeeklyPRCard;
import com.eximbay.okr.model.weeklypr.YearnAndWeekModel;
import com.eximbay.okr.repository.WeeklyActionPlanRepository;
import com.eximbay.okr.repository.WeeklyPRCardRepository;
import com.eximbay.okr.service.Interface.IMemberService;
import com.eximbay.okr.service.Interface.IWeeklyActionPlanService;
import com.eximbay.okr.service.Interface.IWeeklyPRService;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/weekly-prs")
public class WeeklyPRAPI {

    private final MapperFacade mapper;
    public final IWeeklyPRService weeklyPRService;
    public final WeeklyActionPlanRepository weeklyActionPlanRepository;
    public final WeeklyPRCardRepository weeklyPRCardRepository;
    public final IWeeklyActionPlanService weeklyActionPlanService;
    public final IMemberService memberService;

    @PostMapping("/datatables")
    public DataTablesOutput<WeeklyPRCard> getAll(@Valid @RequestBody MemberDatatablesInput input) {
        DataTablesOutput<WeeklyPRCard> weeklyPRCards = weeklyPRService.getDataForDatatables(input);
        return weeklyPRCards;
    }

    @RequestMapping("/test")
    public List<WeeklyPRCardwithMembersDto> test() {
        List<WeeklyPRCardwithMembersDto> weeklyPRCardDtos = weeklyPRService.getMembersDatatables();
        return weeklyPRCardDtos;
    }

    // @RequestMapping("/data")
    // public List<MemberswithWeeklyPRCardDto> data() {
    //     List<MemberswithWeeklyPRCardDto> weeklyPRCardDtos = weeklyPRService.getDatas();
    //     return weeklyPRCardDtos;
    // }


     
    @RequestMapping("/last")
    public List<TestData> last() {
        List<TestData> cards= weeklyPRService.testData();
        return cards;
    }

    @PostMapping("/year")
    public YearnAndWeekModel getYearAndWeek(String year) {
        YearnAndWeekModel selectModels = weeklyPRService.getWeekByYear(Integer.parseInt(year));
        return selectModels;
    }

}
