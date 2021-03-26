package com.eximbay.okr.service;

import com.eximbay.okr.constant.FlagOption;
import com.eximbay.okr.constant.GroupCode;
import com.eximbay.okr.dto.CodeListDto;
import com.eximbay.okr.dto.CompanyDto;
import com.eximbay.okr.entity.CodeList;
import com.eximbay.okr.entity.KeyResult;
import com.eximbay.okr.entity.KeyResultHistory;
import com.eximbay.okr.entity.Objective;
import com.eximbay.okr.entity.ObjectiveHistory;
import com.eximbay.okr.model.company.AddCompanyOkrModel;
import com.eximbay.okr.dto.okr.ObjectiveKeyResultsDto;
import com.eximbay.okr.repository.CodeListRepository;
import com.eximbay.okr.repository.CompanyRepository;
import com.eximbay.okr.repository.KeyResultHistoryRepository;
import com.eximbay.okr.repository.KeyResultRepository;
import com.eximbay.okr.repository.ObjectiveHistoryRepository;
import com.eximbay.okr.repository.ObjectiveRepository;
import com.eximbay.okr.service.Interface.ICompanyOKRService;
import com.eximbay.okr.service.Interface.ICompanyService;
import com.eximbay.okr.service.specification.CodeListQuery;
import com.eximbay.okr.utils.DateTimeUtils;
import lombok.AllArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@AllArgsConstructor
@Service
@Transactional
public class CompanyOKRServiceImpl implements ICompanyOKRService {
    private final ICompanyService companyService;
    private final CompanyRepository companyRepository;
    private final CodeListRepository codeListRepository;
    private final KeyResultRepository keyResultRepository;
    private final ObjectiveRepository objectiveRepository;
    private final ObjectiveHistoryRepository objectiveHistoryRepository;
    private final KeyResultHistoryRepository keyResultHistoryRepository;
    private final CodeListQuery codeListQuery;
    private final MapperFacade mapper;

    public AddCompanyOkrModel buildAddOkrDataModel() {
        AddCompanyOkrModel model = new AddCompanyOkrModel();
        model.setMutedHeader(companyService.getCompany().map(CompanyDto::getLocalName).orElse(""));
        List<CodeList> availableObjLevel = codeListRepository.findAll(codeListQuery.findByGroupCodeAndUseFlagOrderBySortOrderAsc(GroupCode.OBJECTIVE_LEVEL, FlagOption.Y));
        List<CodeList> availableTaskType = codeListRepository.findAll(codeListQuery.findByGroupCodeAndUseFlagOrderBySortOrderAsc(GroupCode.TASK_TYPE, FlagOption.Y));
        List<CodeList> availableTaskMetric = codeListRepository.findAll(codeListQuery.findByGroupCodeAndUseFlagOrderBySortOrderAsc(GroupCode.TASK_METRIC, FlagOption.Y));
        List<CodeList> avalableTaskIndicator = codeListRepository.findAll(codeListQuery.findByGroupCodeAndUseFlagOrderBySortOrderAsc(GroupCode.TASK_INDICATOR, FlagOption.Y));
        model.setObjectiveLevels(mapper.mapAsList(availableObjLevel, CodeListDto.class));
        model.setTaskTypes(mapper.mapAsList(availableTaskType, CodeListDto.class));
        model.setTaskMetrics(mapper.mapAsList(availableTaskMetric, CodeListDto.class));
        model.setTaskIndicators(mapper.mapAsList(avalableTaskIndicator, CodeListDto.class));
        model.setYear(DateTimeUtils.getCurrentYear());
        model.setQuarter(DateTimeUtils.getCurrentQuarter());
        model.setQuarterBeginDate(model.getYear() + "-" + DateTimeUtils.firstDayOfQuarter[model.getQuarter()]);
        model.setQuarterEndDate(model.getYear() + "-" + DateTimeUtils.lastDayOfQuarter[model.getQuarter()]);
        return model;
    }

    @Override
    public void addObjectiveAndKeyResult(ObjectiveKeyResultsDto objectiveKeyResultsDto) {
        String quarterStartDate = (objectiveKeyResultsDto.getYear() + DateTimeUtils.firstDayOfQuarter[objectiveKeyResultsDto.getQuarter()]).replace("-", "");
        String quarterEndDate = (objectiveKeyResultsDto.getYear() + DateTimeUtils.lastDayOfQuarter[objectiveKeyResultsDto.getQuarter()]).replace("-", "");
        Objective objective = new Objective();
        objective.setBeginDate(quarterStartDate);
        objective.setEndDate(quarterEndDate);
        objective.setQuarter(objectiveKeyResultsDto.getQuarter());
        objective.setObjectiveType(Objective.OBJECTIVE_TYPE_COMPANY);
        objective.setCompany(companyRepository.findFirstByOrderByCompanySeq().orElse(null));
        objective.setObjective(objectiveKeyResultsDto.getObjective());
        objective.setYear(objectiveKeyResultsDto.getYear());
        objective.setQuarter(objectiveKeyResultsDto.getQuarter());
        objectiveRepository.save(objective);
        objectiveKeyResultsDto.getKey_results().forEach(kr -> {
            KeyResult keyResult = mapper.map(kr, com.eximbay.okr.entity.KeyResult.class);
            keyResult.setBeginDate(quarterStartDate);
            keyResult.setEndDate(quarterEndDate);
            keyResult.setObjective(objective);
            keyResultRepository.save(keyResult);
            KeyResultHistory keyResultHistory = mapper.map(keyResult, KeyResultHistory.class);
            keyResultHistory.setSourceKeyResult(keyResult);
            keyResultHistory.setObjective(objective);
            keyResultHistoryRepository.save(keyResultHistory);
        });
        ObjectiveHistory objectiveHistory = mapper.map(objective, ObjectiveHistory.class);
        objectiveHistory.setJustification(ObjectiveHistory.DEFAULT_ADD_OBJECTIVE_JUSTIFICATION);
        objectiveHistory.setObjectiveObject(objective);
        objectiveHistoryRepository.save(objectiveHistory);
    }
}
