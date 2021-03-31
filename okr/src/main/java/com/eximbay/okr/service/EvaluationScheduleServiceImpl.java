package com.eximbay.okr.service;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eximbay.okr.constant.FlagOption;
import com.eximbay.okr.constant.GroupCode;
import com.eximbay.okr.dto.codelist.CodeListDto;
import com.eximbay.okr.dto.evaluationschedule.EvaluationScheduleDto;
import com.eximbay.okr.dto.evaluationschedule.EvaluationScheduleDatatablesInput;
import com.eximbay.okr.entity.CodeList;
import com.eximbay.okr.entity.EvaluationSchedule;
import com.eximbay.okr.model.evaluationschedule.EvaluationScheduleModel;
import com.eximbay.okr.repository.CodeListRepository;
import com.eximbay.okr.repository.EvaluationScheduleRepository;
import com.eximbay.okr.service.Interface.IEvaluationScheduleService;
import com.eximbay.okr.service.specification.EvaluationScheduleQuery;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import ma.glasnost.orika.MapperFacade;

@Service
@Data
@AllArgsConstructor
@Transactional
public class EvaluationScheduleServiceImpl implements IEvaluationScheduleService {

    private final CodeListRepository codeListRepository;
    private final EvaluationScheduleRepository evaluationScheduleRepository;
    private final EvaluationScheduleQuery evaluationScheduleQuery;
    private final MapperFacade mapper;

    @Override
    public List<EvaluationScheduleDto> findAll() {
        List<EvaluationSchedule> evaluationSchedules = Lists.newArrayList(evaluationScheduleRepository.findAll());
        return mapper.mapAsList(evaluationSchedules, EvaluationScheduleDto.class);
    }

    @Override
    public Optional<EvaluationScheduleDto> findById(Integer id) {
        Optional<EvaluationSchedule> evalutaionSchedule = evaluationScheduleRepository.findById(id);
        return evalutaionSchedule.map(m -> mapper.map(m, EvaluationScheduleDto.class));
    }

    @Override
    public void remove(EvaluationScheduleDto evaluationScheduleDto) {
        EvaluationSchedule evaluationSchedule = mapper.map(evaluationScheduleDto, EvaluationSchedule.class);
        evaluationScheduleRepository.delete(evaluationSchedule);

    }

    @Override
    public EvaluationScheduleDto save(EvaluationScheduleDto evaluationScheduleDto) {
        EvaluationSchedule evaluationSchedule = mapper.map(evaluationScheduleDto, EvaluationSchedule.class);
        evaluationSchedule = evaluationScheduleRepository.save(evaluationSchedule);
        return mapper.map(evaluationSchedule, EvaluationScheduleDto.class);
    }

    @Override
    public EvaluationScheduleModel buildEvaluationScheduleModel() {
        EvaluationScheduleModel scheduleModel = new EvaluationScheduleModel();
        List<CodeList> availableEvaluationTypes = codeListRepository
                .findByGroupCodeAndUseFlagOrderBySortOrderAsc(GroupCode.EVALUATION_TYPE, FlagOption.Y);
        scheduleModel.setEvaluationTypes(mapper.mapAsList(availableEvaluationTypes, CodeListDto.class));

        return scheduleModel;
    }

    @Override
    public DataTablesOutput<EvaluationSchedule> getDataForDatatables(EvaluationScheduleDatatablesInput input) {
        DataTablesOutput<EvaluationSchedule> output = evaluationScheduleRepository.findAll(input,
        evaluationScheduleQuery.buildQueryForDatatables(input));
        return output;
    }

}
