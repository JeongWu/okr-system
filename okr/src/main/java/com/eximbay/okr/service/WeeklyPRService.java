package com.eximbay.okr.service;

import java.util.List;
import java.util.stream.Collectors;

import com.eximbay.okr.constant.FlagOption;
import com.eximbay.okr.dto.weekly.DateInput;
import com.eximbay.okr.dto.weekly.MemberDatatablesInput;
import com.eximbay.okr.dto.weekly.WeeklyPRCardDto;
import com.eximbay.okr.dto.weekly.WeeklyPRCardwithMembersDto;
import com.eximbay.okr.entity.WeeklyActionPlan;
import com.eximbay.okr.entity.WeeklyPRCard;
import com.eximbay.okr.model.weeklypr.WeeklyPRMemberModel;
import com.eximbay.okr.model.weeklypr.YearnAndWeekModel;
import com.eximbay.okr.repository.MemberRepository;
import com.eximbay.okr.repository.WeeklyActionPlanRepository;
import com.eximbay.okr.repository.WeeklyPRCardRepository;
import com.eximbay.okr.service.Interface.IWeeklyActionPlanService;
import com.eximbay.okr.service.Interface.IWeeklyPRService;
import com.eximbay.okr.service.specification.WeeklyPRCardQuery;

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
    private final WeeklyPRCardQuery weeklyPRCardQuery;
    public final WeeklyActionPlanRepository weeklyActionPlanRepository;
    public final WeeklyPRCardRepository weeklyPRCardRepository;
    public final IWeeklyActionPlanService weeklyActionPlanService;

    @Override
    public WeeklyPRMemberModel buildViewAllMembersModel() {

        WeeklyPRMemberModel weeklyPRsMemberModel = new WeeklyPRMemberModel();
        String totalNum = Long.toString(memberRepository.countByUseFlag(FlagOption.Y));
        weeklyPRsMemberModel.setMutedHeader(totalNum + " Total");

        List<WeeklyPRCardDto> weeklyPRCards = mapper.mapAsList(weeklyPRCardRepository.findAll(), WeeklyPRCardDto.class);
        List<Integer> years = weeklyPRCards.stream().map(w -> w.getYear()).distinct().collect(Collectors.toList());
        List<String> memberNames=weeklyPRCards.stream().map(w->w.getMember().getLocalName()).distinct().collect(Collectors.toList());
        weeklyPRsMemberModel.setYears(years);
        weeklyPRsMemberModel.setMemberNames(memberNames);
        return weeklyPRsMemberModel;
    }

    @Override
    public DataTablesOutput<WeeklyPRCard> getDataForDatatables(MemberDatatablesInput input) {
    
        // List<WeeklyPRCard> weeklyPRCards=weeklyPRCardRepository.findAll();
        // List<WeeklyPRCardwithMembersDto> weeklyPRCardDtos=mapper.mapAsList(weeklyPRCards, WeeklyPRCardwithMembersDto.class);
        // for(int i=0;i<weeklyPRCardDtos.size();i++){
        //     Integer weeklySeq=weeklyPRCardDtos.get(i).getWeeklySeq();
        //     List<WeeklyActionPlan> weeklyActionPlans=weeklyActionPlanRepository.findByWeeklySeq(weeklySeq);
        //     Integer sumReview=weeklyActionPlans.stream().map(w->w.getReview()).mapToInt(Integer::valueOf).sum();
        //     Integer sumActionPlan=weeklyActionPlans.stream().map(w->w.getActionPlan()).mapToInt(Integer::valueOf).sum();
        
        //     weeklyPRCardDtos.get(i).setSumReview(sumReview);
        //     weeklyPRCardDtos.get(i).setSumActionPlan(sumActionPlan);
        // }
        DataTablesOutput<WeeklyPRCard> output = weeklyPRCardRepository.findAll(input,
        weeklyPRCardQuery.buildQueryForDatatables(input));
        return output;
        // return null;
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

    @Override
    public List<WeeklyPRCardwithMembersDto> getMembersDatatables() {
        List<WeeklyPRCard> weeklyPRCards=weeklyPRCardRepository.findAll();
        List<WeeklyPRCardwithMembersDto> weeklyPRCardDtos=mapper.mapAsList(weeklyPRCards, WeeklyPRCardwithMembersDto.class);
        List<WeeklyActionPlan> weeklyActionPlans=weeklyActionPlanRepository.findAll();
        for(int i=0;i<weeklyPRCardDtos.size();i++){
            Integer weeklySeq=weeklyPRCardDtos.get(i).getWeeklySeq();
            List<WeeklyActionPlan> plans=weeklyActionPlans.stream().filter(m->m.getWeeklyPRCard().getWeeklySeq().equals(weeklySeq)).collect(Collectors.toList());
            Integer sumReview=plans.stream().map(w->w.getReview()).mapToInt(Integer::valueOf).sum();
            Integer sumActionPlan=plans.stream().map(w->w.getActionPlan()).mapToInt(Integer::valueOf).sum();
        
            weeklyPRCardDtos.get(i).setSumReview(sumReview);
            weeklyPRCardDtos.get(i).setSumActionPlan(sumActionPlan);
        }
        return weeklyPRCardDtos;
    }

}
