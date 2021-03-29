package com.eximbay.okr.service;

import com.eximbay.okr.constant.FlagOption;
import com.eximbay.okr.constant.GroupCode;
import com.eximbay.okr.constant.Subheader;
import com.eximbay.okr.dto.CodeListDto;
import com.eximbay.okr.dto.CompanyDto;
import com.eximbay.okr.dto.objective.ObjectiveDto;
import com.eximbay.okr.entity.CodeList;
import com.eximbay.okr.entity.KeyResult;
import com.eximbay.okr.entity.KeyResultHistory;
import com.eximbay.okr.entity.Objective;
import com.eximbay.okr.entity.ObjectiveHistory;
import com.eximbay.okr.exception.UserException;
import com.eximbay.okr.model.company.AddCompanyOkrModel;
import com.eximbay.okr.model.company.EditCompanyOkrModel;
import com.eximbay.okr.model.company.OkrCommonModel;
import com.eximbay.okr.repository.CodeListRepository;
import com.eximbay.okr.repository.CompanyRepository;
import com.eximbay.okr.repository.KeyResultHistoryRepository;
import com.eximbay.okr.repository.KeyResultRepository;
import com.eximbay.okr.repository.ObjectiveHistoryRepository;
import com.eximbay.okr.repository.ObjectiveRepository;
import com.eximbay.okr.service.Interface.ICompanyOKRService;
import com.eximbay.okr.service.Interface.ICompanyService;
import com.eximbay.okr.service.Interface.IObjectiveService;
import com.eximbay.okr.service.specification.CodeListQuery;
import com.eximbay.okr.utils.DateTimeUtils;
import lombok.AllArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
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
    private final IObjectiveService objectiveService;
    private final CodeListQuery codeListQuery;
    private final MapperFacade mapper;

    public AddCompanyOkrModel buildAddOkrDataModel() {
        AddCompanyOkrModel model = new AddCompanyOkrModel();
        this.buildCommonModel(model);
        model.setMutedHeader(companyService.getCompany().map(CompanyDto::getLocalName).orElse(""));
        model.setYear(DateTimeUtils.getCurrentYear());
        model.setQuarter(DateTimeUtils.getCurrentQuarter());
        model.setQuarterBeginDate(model.getYear() + "-" + DateTimeUtils.firstDayOfQuarter[model.getQuarter()]);
        model.setQuarterEndDate(model.getYear() + "-" + DateTimeUtils.lastDayOfQuarter[model.getQuarter()]);
        model.setSumProportionOfOtherObjectives(objectiveRepository.sumProportionsOfActiveObjectivesInQuarter(Objective.OBJECTIVE_TYPE_COMPANY, model.getYear(), model.getQuarter()));
        return model;
    }

    private void buildCommonModel(OkrCommonModel model) {
        List<CodeList> availableObjLevel = codeListRepository.findByGroupCodeAndUseFlagOrderBySortOrderAsc(GroupCode.OBJECTIVE_LEVEL, FlagOption.Y);
        List<CodeList> availableTaskType = codeListRepository.findByGroupCodeAndUseFlagOrderBySortOrderAsc(GroupCode.TASK_TYPE, FlagOption.Y);
        List<CodeList> availableTaskMetric = codeListRepository.findByGroupCodeAndUseFlagOrderBySortOrderAsc(GroupCode.TASK_METRIC, FlagOption.Y);
        List<CodeList> avalableTaskIndicator = codeListRepository.findByGroupCodeAndUseFlagOrderBySortOrderAsc(GroupCode.TASK_INDICATOR, FlagOption.Y);
        model.setObjectiveLevels(mapper.mapAsList(availableObjLevel, CodeListDto.class));
        model.setTaskTypes(mapper.mapAsList(availableTaskType, CodeListDto.class));
        model.setTaskMetrics(mapper.mapAsList(availableTaskMetric, CodeListDto.class));
        model.setTaskIndicators(mapper.mapAsList(avalableTaskIndicator, CodeListDto.class));
    }

    @Override
    public void addObjectiveAndKeyResult(ObjectiveDto objectiveDto) {
        String quarterStartDate = (objectiveDto.getYear() + DateTimeUtils.firstDayOfQuarter[objectiveDto.getQuarter()]).replace("-", "");
        String quarterEndDate = (objectiveDto.getYear() + DateTimeUtils.lastDayOfQuarter[objectiveDto.getQuarter()]).replace("-", "");
        Objective objective = mapper.map(objectiveDto, Objective.class);
        objective.setBeginDate(quarterStartDate);
        objective.setEndDate(quarterEndDate);
        objective.setObjectiveType(Objective.OBJECTIVE_TYPE_COMPANY);
        objective.setCompany(companyRepository.findFirstByOrderByCompanySeq().orElse(null));
        objective.setLastUpdateDate(Instant.now());
        objectiveRepository.save(objective);
        objectiveDto.getKeyResults().forEach(kr -> {
            KeyResult keyResult = mapper.map(kr, KeyResult.class);
            keyResult.setBeginDate(quarterStartDate);
            keyResult.setEndDate(quarterEndDate);
            keyResult.setObjective(objective);
            keyResult.setLastUpdatedDate(Instant.now());
            keyResultRepository.save(keyResult);
            KeyResultHistory keyResultHistory = mapper.map(keyResult, KeyResultHistory.class);
            keyResultHistory.setSourceKeyResult(keyResult);
            keyResultHistory.setObjective(objective);
            keyResultHistory.setJustification(KeyResultHistory.DEFAULT_ADD_NEW_KEY_RESULT_JUSTIFICATION);
            keyResultHistoryRepository.save(keyResultHistory);
        });
        ObjectiveHistory objectiveHistory = mapper.map(objective, ObjectiveHistory.class);
        objectiveHistory.setJustification(ObjectiveHistory.DEFAULT_ADD_OBJECTIVE_JUSTIFICATION);
        objectiveHistory.setObjectiveObject(objective);
        objectiveHistoryRepository.save(objectiveHistory);
    }

    @Override
    public EditCompanyOkrModel buildEditOkrDataModel(int objectiveId) {
        EditCompanyOkrModel model = new EditCompanyOkrModel();
        model.setSubheader(Subheader.EDIT_OKR);
        this.buildCommonModel(model);
        model.setMutedHeader(companyService.getCompany().map(CompanyDto::getLocalName).orElse(""));
        ObjectiveDto objectiveDto = objectiveService.findById(objectiveId).orElseThrow(() -> new UserException("Cannot find objective with ID: " + objectiveId));
        int sumObjectivesProportions = objectiveRepository.sumProportionsOfActiveObjectivesInQuarter(Objective.OBJECTIVE_TYPE_COMPANY, objectiveDto.getYear(), objectiveDto.getQuarter());
        objectiveDto.setSumProportionOfOtherObjectives(sumObjectivesProportions - objectiveDto.getProportion());
        model.setObjectiveDto(objectiveDto);
        return model;
    }
}
