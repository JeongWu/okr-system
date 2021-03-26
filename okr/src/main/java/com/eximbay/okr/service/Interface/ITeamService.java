package com.eximbay.okr.service.Interface;

import java.util.List;
import java.util.Optional;

import com.eximbay.okr.model.team.TeamViewOkrModel;
import org.springframework.data.domain.Pageable;

import com.eximbay.okr.dto.MemberDto;
import com.eximbay.okr.dto.TeamDto;
import com.eximbay.okr.dto.TeamMemberDto;
import com.eximbay.okr.entity.Team;
import com.eximbay.okr.model.AllDetailsTeamModel;
import com.eximbay.okr.model.AllTeamUpdateModel;
import com.eximbay.okr.model.EditForViewAllTeamsModel;
import com.eximbay.okr.model.EditTeamModel;
import com.eximbay.okr.model.TeamListTableModel;
import com.eximbay.okr.model.TeamUpdateFormModel;
import com.eximbay.okr.model.WireframeModel;
import com.eximbay.okr.model.team.DivisionListModel;
import com.eximbay.okr.model.team.TeamAddModel;

public interface ITeamService extends ISerivce<TeamDto, Integer> {

    List<TeamDto> findAllInUse();
    List<TeamListTableModel> buildListTableModel();
    long countAllTeam();
    AllDetailsTeamModel buildAllDetailsTeamModel(Pageable pageable);
    boolean isCurrentMemberTeamLeadOrManager(List<TeamMemberDto> teamMemberDtos);
    Optional<MemberDto> getCurrentLoginUser();
    int countByUseFlag(String useFlag);
    
    EditTeamModel buildEditTeamModel(Integer id);
    void updateFormModel(TeamUpdateFormModel updateFormModel); 
    Team addTeam(TeamAddModel teamAddModel);

    EditForViewAllTeamsModel buildEditAllTeamsModel(Integer id);
    void updateForViewAllTeamModel(AllTeamUpdateModel allTeamUpdateModel); 
	
    TeamViewOkrModel buildTeamOkrModel(Integer teamSeq, String quarter);
	
}
