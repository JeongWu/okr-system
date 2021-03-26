package com.eximbay.okr.service;

import com.eximbay.okr.dto.TeamDto;
import com.eximbay.okr.model.TeamForWireframeModel;
import com.eximbay.okr.model.WireframeModel;
import com.eximbay.okr.service.Interface.ICommonService;
import com.eximbay.okr.service.Interface.ICompanyService;
import com.eximbay.okr.service.Interface.ITeamMemberService;
import com.eximbay.okr.service.Interface.ITeamService;
import lombok.AllArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommonServiceImpl implements ICommonService {
    private final MapperFacade mapper;
    private final ITeamService teamService;
    private final ITeamMemberService teamMemberService;
    private final ICompanyService companyService;

    @Override
    public WireframeModel buildWireframeModel() {
        WireframeModel wireframeModel = new WireframeModel();
        List<TeamDto> teams = teamService.findAllInUse();
        List<TeamDto> teamsWithLeaderOrManager = teamMemberService.addLeaderToTeamList(teams);
        List<TeamForWireframeModel> teamForWireframeModels = mapper.mapAsList(teamsWithLeaderOrManager,
                TeamForWireframeModel.class);
        wireframeModel.setTeams(teamForWireframeModels);
        wireframeModel.setCompany(companyService.getCompany().orElse(null));
        return wireframeModel;
    }
}
