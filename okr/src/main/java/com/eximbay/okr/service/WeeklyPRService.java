package com.eximbay.okr.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.eximbay.okr.constant.FlagOption;
import com.eximbay.okr.dto.member.MemberDto;
import com.eximbay.okr.dto.weeklypr.MembersWithWeeklyPrCardDto;
import com.eximbay.okr.dto.weeklypr.WeeklyPrCardDto;
import com.eximbay.okr.entity.WeeklyActionPlan;
import com.eximbay.okr.entity.WeeklyPrCard;
import com.eximbay.okr.model.weeklypr.WeeklyPrMemberModel;
import com.eximbay.okr.model.weeklypr.YearnAndWeekModel;
import com.eximbay.okr.repository.MemberRepository;
import com.eximbay.okr.repository.WeeklyActionPlanRepository;
import com.eximbay.okr.repository.WeeklyPrCardRepository;
import com.eximbay.okr.service.Interface.IMemberService;
import com.eximbay.okr.service.Interface.ITeamService;
import com.eximbay.okr.service.Interface.IWeeklyPrService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;

@Service
@RequiredArgsConstructor
@Transactional
public class WeeklyPrService implements IWeeklyPrService {

    private final MapperFacade mapper;
    public final WeeklyActionPlanRepository weeklyActionPlanRepository;
    public final WeeklyPrCardRepository weeklyPrCardRepository;
    private final MemberRepository memberRepository;
    public final IMemberService memberService;
    public final ITeamService teamService;

    @Override
    public WeeklyPrMemberModel buildViewAllMembersModel() {
        WeeklyPrMemberModel weeklyPrMemberModel = new WeeklyPrMemberModel();
        String totalNum = Long.toString(memberRepository.countByUseFlag(FlagOption.Y));
        weeklyPrMemberModel.setMutedHeader(totalNum + " Total");

        List<WeeklyPrCardDto> weeklyPRCards = mapper.mapAsList(weeklyPrCardRepository.findAll(), WeeklyPrCardDto.class);
        List<Integer> years = weeklyPRCards.stream().map(w -> w.getYear()).distinct().collect(Collectors.toList());
        List<String> teamNames = teamService.findAllInUse().stream().map(t -> t.getLocalName())
                .collect(Collectors.toList());
        weeklyPrMemberModel.setYears(years);
        weeklyPrMemberModel.setTeamNames(teamNames);
        return weeklyPrMemberModel;
    }

    @Override
    public YearnAndWeekModel getWeekByYear(Integer year) {
        YearnAndWeekModel yearnAndWeekModel = new YearnAndWeekModel();
        List<WeeklyPrCard> weeklyPRCards = weeklyPrCardRepository.findByYear(year);
        List<Integer> weeks = weeklyPRCards.stream().map(w -> w.getWeek()).distinct().collect(Collectors.toList());
        yearnAndWeekModel.setYear(year);
        yearnAndWeekModel.setWeeks(weeks);
        return yearnAndWeekModel;
    }

    @Override
    public List<MembersWithWeeklyPrCardDto> getDataForDatatables() {

        List<MemberDto> members = memberService.findActiveMembers();

        List<MembersWithWeeklyPrCardDto> membersWithWeeklyPrCardDtos = new ArrayList<>();

        for (int i = 0; i < members.size(); i++) {
            Integer memberSeq = members.get(i).getMemberSeq();
            List<WeeklyPrCard> weeklyPrcards = weeklyPrCardRepository.findByMemberSeq(memberSeq);
            MembersWithWeeklyPrCardDto membersWithWeeklyPrCardDto = new MembersWithWeeklyPrCardDto();

            if (weeklyPrcards.size() != 0) {
                for (int j = 0; j < weeklyPrcards.size(); j++) {
                    membersWithWeeklyPrCardDto = new MembersWithWeeklyPrCardDto();
                    membersWithWeeklyPrCardDto.setMember(mapper.map(members.get(i), MemberDto.class));
                    membersWithWeeklyPrCardDto.setMemberSeq(memberSeq);
                    Integer weeklySeq = weeklyPrcards.get(j).getWeeklySeq();
                    membersWithWeeklyPrCardDto.setWeeklySeq(weeklySeq);
                    WeeklyPrCard weeklyPrCard = weeklyPrCardRepository.findByWeeklySeq(weeklySeq);

                    membersWithWeeklyPrCardDto.setWeeklyPrCard(sumReviewAndActionPlan(weeklyPrCard));

                    membersWithWeeklyPrCardDtos.add(membersWithWeeklyPrCardDto);
                }
            } else {
                membersWithWeeklyPrCardDto.setMember(mapper.map(members.get(i), MemberDto.class));
                membersWithWeeklyPrCardDto.setMemberSeq(memberSeq);
                membersWithWeeklyPrCardDtos.add(membersWithWeeklyPrCardDto);

            }

        }

        return membersWithWeeklyPrCardDtos;
    }

    @Override
    public WeeklyPrCardDto sumReviewAndActionPlan(WeeklyPrCard weeklyPrCard) {
        WeeklyPrCardDto weeklyPrCardDto=mapper.map(weeklyPrCard,WeeklyPrCardDto.class);

        List<WeeklyActionPlan> weeklyActionPlans=weeklyActionPlanRepository.findByWeeklySeq(weeklyPrCardDto.getWeeklySeq());
        Integer sumReview = weeklyActionPlans.stream().map(w -> w.getReview()).mapToInt(Integer::valueOf).sum();
        Integer sumActionPlan = weeklyActionPlans.stream().map(w -> w.getActionPlan()).mapToInt(Integer::valueOf).sum();

        weeklyPrCardDto.setSumReview(sumReview);
        weeklyPrCardDto.setSumActionPlan(sumActionPlan);

        return weeklyPrCardDto;
    }

}
