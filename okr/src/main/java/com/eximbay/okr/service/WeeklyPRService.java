package com.eximbay.okr.service;

import java.util.List;
import java.util.stream.Collectors;

import com.eximbay.okr.constant.FlagOption;
import com.eximbay.okr.dto.weeklypr.MemberDatatablesInput;
import com.eximbay.okr.dto.weeklypr.WeeklyPRCardDto;
import com.eximbay.okr.entity.WeeklyPRCard;
import com.eximbay.okr.model.weeklypr.WeeklyPRMemberModel;
import com.eximbay.okr.model.weeklypr.YearnAndWeekModel;
import com.eximbay.okr.repository.MemberRepository;
import com.eximbay.okr.repository.WeeklyPRCardRepository;
import com.eximbay.okr.service.Interface.IWeeklyPRService;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;

@Service
@RequiredArgsConstructor
@Transactional
public class WeeklyPRService implements IWeeklyPRService {

    private final MapperFacade mapper;
    private final MemberRepository memberRepository;
    private final WeeklyPRCardRepository weeklyPRCardRepository;

    @Override
    public WeeklyPRMemberModel buildViewAllMembersModel() {

        WeeklyPRMemberModel weeklyPRsMemberModel = new WeeklyPRMemberModel();
        String totalNum = Long.toString(memberRepository.countByUseFlag(FlagOption.Y));
        weeklyPRsMemberModel.setMutedHeader(totalNum + " Total");

        List<WeeklyPRCardDto> weeklyPRCards = mapper.mapAsList(weeklyPRCardRepository.findAll(), WeeklyPRCardDto.class);
        List<Integer> years = weeklyPRCards.stream().map(w -> w.getYear()).distinct().collect(Collectors.toList());
        weeklyPRsMemberModel.setYears(years);
        return weeklyPRsMemberModel;
    }

    @Override
    public DataTablesOutput<WeeklyPRCard> getDataForDatatables(MemberDatatablesInput input) {
        // DataTablesOutput<WeeklyPRCard> output = weeklyPRCardRepository.findAll();
        // return output;
        return null;
    }

    @Override
    public YearnAndWeekModel getWeekByYear(Integer year) {
        YearnAndWeekModel yearnAndWeekModel = new YearnAndWeekModel();
        List<WeeklyPRCard> weeklyPRCards = weeklyPRCardRepository.findByYear(year);
        List<Integer> weeks = weeklyPRCards.stream().map(w -> w.getWeek()).distinct().collect(Collectors.toList());
        yearnAndWeekModel.setYear(year);
        yearnAndWeekModel.setWeeks(weeks);
        return yearnAndWeekModel;
    }

}
