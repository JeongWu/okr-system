package com.eximbay.okr.service;

import java.util.List;
import java.util.Optional;

import com.eximbay.okr.constant.FlagOption;
import com.eximbay.okr.constant.GroupCode;
import com.eximbay.okr.dto.CodeListDto;
import com.eximbay.okr.dto.schedule.EvaluationScheduleDto;
import com.eximbay.okr.dto.schedule.ScheduleDatatablesInput;
import com.eximbay.okr.entity.CodeList;
import com.eximbay.okr.entity.EvaluationSchedule;
import com.eximbay.okr.model.schedule.ScheduleModel;
import com.eximbay.okr.repository.CodeListRepository;
import com.eximbay.okr.repository.ScheduleRepository;
import com.eximbay.okr.service.Interface.IScheduleService;
import com.eximbay.okr.service.specification.ScheduleQuery;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import ma.glasnost.orika.MapperFacade;

@Service
@Data
@AllArgsConstructor
@Transactional
public class ScheduleServiceImpl implements IScheduleService {

    private final CodeListRepository codeListRepository;
    private final ScheduleRepository scheduleRepository;
    private final ScheduleQuery scheduleQuery;
    private final MapperFacade mapper;

    @Override
    public List<EvaluationScheduleDto> findAll() {
        List<EvaluationSchedule> evaluationSchedules = Lists.newArrayList(scheduleRepository.findAll());
        return mapper.mapAsList(evaluationSchedules, EvaluationScheduleDto.class);
    }

    @Override
    public Optional<EvaluationScheduleDto> findById(Integer id) {
        Optional<EvaluationSchedule> evalutaionSchedule = scheduleRepository.findById(id);
        return evalutaionSchedule.map(m -> mapper.map(m, EvaluationScheduleDto.class));
    }

    @Override
    public void remove(EvaluationScheduleDto evaluationScheduleDto) {
        EvaluationSchedule evaluationSchedule = mapper.map(evaluationScheduleDto, EvaluationSchedule.class);
        scheduleRepository.delete(evaluationSchedule);

    }

    @Override
    public EvaluationScheduleDto save(EvaluationScheduleDto evaluationScheduleDto) {
        EvaluationSchedule evaluationSchedule = mapper.map(evaluationScheduleDto, EvaluationSchedule.class);
        evaluationSchedule = scheduleRepository.save(evaluationSchedule);
        return mapper.map(evaluationSchedule, EvaluationScheduleDto.class);
    }

    @Override
    public ScheduleModel buildScheduleModel() {
        ScheduleModel scheduleModel = new ScheduleModel();
        List<CodeList> availableEvaluationTypes = codeListRepository
                .findByGroupCodeAndUseFlagOrderBySortOrderAsc(GroupCode.EVALUATION_TYPE, FlagOption.Y);
        scheduleModel.setEvaluationTypes(mapper.mapAsList(availableEvaluationTypes, CodeListDto.class));

        return scheduleModel;
    }

    @Override
    public DataTablesOutput<EvaluationSchedule> getDataForDatatables(ScheduleDatatablesInput input) {
        DataTablesOutput<EvaluationSchedule> output = scheduleRepository.findAll(input,
        scheduleQuery.buildQueryForDatatables(input));
        return output;
    }

}
