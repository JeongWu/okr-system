package com.eximbay.okr.service;

import com.eximbay.okr.constant.FlagOption;
import com.eximbay.okr.model.weeklyprs.WeeklyPRsMemberModel;
import com.eximbay.okr.repository.MemberRepository;
import com.eximbay.okr.service.Interface.IWeeklyPRsService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class WeeklyPRsService implements IWeeklyPRsService {

    private final MemberRepository memberRepository;

    @Override
    public WeeklyPRsMemberModel buildViewAllMembersModel() {

        WeeklyPRsMemberModel weeklyPRsMemberModel = new WeeklyPRsMemberModel();
        String totalNum = Long.toString(memberRepository.countByUseFlag(FlagOption.Y));
        weeklyPRsMemberModel.setMutedHeader(totalNum+" Total");

        return weeklyPRsMemberModel;
    }

}
