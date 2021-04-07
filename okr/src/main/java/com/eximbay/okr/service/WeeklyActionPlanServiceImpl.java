package com.eximbay.okr.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.eximbay.okr.dto.weekly.WeeklyActionPlanDto;
import com.eximbay.okr.entity.WeeklyActionPlan;
import com.eximbay.okr.repository.WeeklyActionPlanRepository;
import com.eximbay.okr.service.Interface.IWeeklyActionPlanService;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;

@Service
@RequiredArgsConstructor
@Transactional
public class WeeklyActionPlanServiceImpl implements IWeeklyActionPlanService {
    private final WeeklyActionPlanRepository weeklyActionPlanRepository;
    private final MapperFacade mapper;

    @Override
    public List<WeeklyActionPlanDto> findAll() {
        List<WeeklyActionPlan> weeklyActionPlans = weeklyActionPlanRepository.findAll();
        return mapper.mapAsList(weeklyActionPlans, WeeklyActionPlanDto.class);
    }

    @Override
    public Optional<WeeklyActionPlanDto> findById(Integer id) {
        Optional<WeeklyActionPlan> weeklyActionPlan = weeklyActionPlanRepository.findById(id);
        return weeklyActionPlan.map(m -> mapper.map(m, WeeklyActionPlanDto.class));
    }

    @Override
    public void remove(WeeklyActionPlanDto weeklyActionPlanDto) {
        WeeklyActionPlan weeklyActionPlan = mapper.map(weeklyActionPlanDto, WeeklyActionPlan.class);
        weeklyActionPlanRepository.delete(weeklyActionPlan);
    }

    @Override
    public WeeklyActionPlanDto save(WeeklyActionPlanDto weeklyActionPlanDto) {
        WeeklyActionPlan weeklyActionPlan = mapper.map(weeklyActionPlanDto, WeeklyActionPlan.class);
        weeklyActionPlan = weeklyActionPlanRepository.save(weeklyActionPlan);
        return mapper.map(weeklyActionPlan, WeeklyActionPlanDto.class);
    }

}
