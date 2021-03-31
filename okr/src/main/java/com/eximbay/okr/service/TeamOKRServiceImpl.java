package com.eximbay.okr.service;

import com.eximbay.okr.constant.FlagOption;
import com.eximbay.okr.constant.GroupCode;
import com.eximbay.okr.constant.Subheader;
import com.eximbay.okr.dto.codelist.CodeListDto;
import com.eximbay.okr.dto.objective.ObjectiveDto;
import com.eximbay.okr.entity.CodeList;
import com.eximbay.okr.entity.KeyResult;
import com.eximbay.okr.entity.KeyResultHistory;
import com.eximbay.okr.entity.Objective;
import com.eximbay.okr.entity.ObjectiveHistory;
import com.eximbay.okr.entity.Team;
import com.eximbay.okr.exception.UserException;
import com.eximbay.okr.model.team.AddTeamOkrModel;
import com.eximbay.okr.model.team.EditTeamOkrModel;
import com.eximbay.okr.model.team.TeamOkrCommonModel;
import com.eximbay.okr.repository.CodeListRepository;
import com.eximbay.okr.repository.CompanyRepository;
import com.eximbay.okr.repository.KeyResultHistoryRepository;
import com.eximbay.okr.repository.KeyResultRepository;
import com.eximbay.okr.repository.ObjectiveHistoryRepository;
import com.eximbay.okr.repository.ObjectiveRepository;
import com.eximbay.okr.repository.TeamRepository;
import com.eximbay.okr.service.Interface.ITeamOKRService;
import com.eximbay.okr.utils.DateTimeUtils;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TeamOKRServiceImpl implements ITeamOKRService {

    private final MapperFacade mapper;
    private final CompanyRepository companyRepository;
    private final TeamRepository teamRepository;
    private final CodeListRepository codeListRepository;
    private final KeyResultRepository keyResultRepository;
    private final ObjectiveRepository objectiveRepository;
    private final ObjectiveHistoryRepository objectiveHistoryRepository;
    private final KeyResultHistoryRepository keyResultHistoryRepository;

    @Override
    public AddTeamOkrModel buildAddTeamOkrModel(int teamId) {
        AddTeamOkrModel model = new AddTeamOkrModel();
        this.buildCommonModel(model);
        model.setMutedHeader(teamRepository.findById(teamId).map(Team::getLocalName).orElse(""));
        model.setYear(DateTimeUtils.getCurrentYear());
        model.setQuarter(DateTimeUtils.getCurrentQuarter());
        model.setQuarterBeginDate(model.getYear() + "-" + DateTimeUtils.firstDayOfQuarter[model.getQuarter()]);
        model.setQuarterEndDate(model.getYear() + "-" + DateTimeUtils.lastDayOfQuarter[model.getQuarter()]);
        model.setTeamId(teamId);
        model.setSumProportionOfOtherObjectives(objectiveRepository.sumActiveProportionOfTeamByYearAndQuarter(teamId, model.getYear(), model.getQuarter()));
        return model;
    }

    private void buildCommonModel(TeamOkrCommonModel model) {
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
    public void addObjectiveAndKeyResult(int teamId, ObjectiveDto objectiveDto) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new UserException("Cannot find team with ID: " + teamId));
        String quarterStartDate = (objectiveDto.getYear() + DateTimeUtils.firstDayOfQuarter[objectiveDto.getQuarter()]).replace("-", "");
        String quarterEndDate = (objectiveDto.getYear() + DateTimeUtils.lastDayOfQuarter[objectiveDto.getQuarter()]).replace("-", "");
        Objective objective = mapper.map(objectiveDto, Objective.class);
        objective.setBeginDate(quarterStartDate);
        objective.setEndDate(quarterEndDate);
        objective.setObjectiveType(Objective.OBJECTIVE_TYPE_TEAM);
        objective.setTeam(team);
        objective.setCompany(companyRepository.findFirstByOrderByCompanySeq().orElse(null));
        objective.setLatestUpdateDt(Instant.now());
        objectiveRepository.save(objective);
        objectiveDto.getKeyResults().forEach(kr -> {
            KeyResult keyResult = mapper.map(kr, KeyResult.class);
            keyResult.setBeginDate(quarterStartDate);
            keyResult.setEndDate(quarterEndDate);
            keyResult.setObjective(objective);
            keyResult.setLatestUpdateDt(Instant.now());
            keyResultRepository.save(keyResult);
            KeyResultHistory keyResultHistory = mapper.map(keyResult, KeyResultHistory.class);
            keyResultHistory.setKeyResultObject(keyResult);
            keyResultHistory.setJustification(KeyResultHistory.DEFAULT_ADD_NEW_KEY_RESULT_JUSTIFICATION);
            keyResultHistoryRepository.save(keyResultHistory);
        });
        ObjectiveHistory objectiveHistory = mapper.map(objective, ObjectiveHistory.class);
        objectiveHistory.setJustification(ObjectiveHistory.DEFAULT_ADD_OBJECTIVE_JUSTIFICATION);
        objectiveHistory.setObjectiveObject(objective);
        objectiveHistoryRepository.save(objectiveHistory);
    }

    @Override
    public EditTeamOkrModel buildEditTeamOkrDataModel(int teamId, int objectiveId) {
        EditTeamOkrModel model = new EditTeamOkrModel();
        model.setSubheader(Subheader.EDIT_OKR);
        this.buildCommonModel(model);
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new UserException("Cannot find team with ID: " + teamId));
        model.setMutedHeader(team.getLocalName());
        Objective objective = objectiveRepository.findByTeamAndObjectiveSeq(team, objectiveId).orElseThrow(() -> new UserException("Cannot find objective: " + objectiveId + " of Team: " + teamId));
        ObjectiveDto objectiveDto = mapper.map(objective, ObjectiveDto.class);
        int sumActiveObjectiveOfTeamInQuarter = objectiveRepository.sumActiveProportionOfTeamByYearAndQuarter(teamId,objective.getYear(), objective.getQuarter());
        objectiveDto.setSumProportionOfOtherObjectives(sumActiveObjectiveOfTeamInQuarter - objectiveDto.getProportion());
        model.setObjectiveDto(objectiveDto);
        return model;
    }
}
