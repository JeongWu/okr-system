package com.eximbay.okr.service.Interface;

import com.eximbay.okr.dto.member.MemberDto;
import com.eximbay.okr.dto.team.TeamDto;
import com.eximbay.okr.dto.teammember.TeamMemberDto;
import com.eximbay.okr.entity.Team;
import com.eximbay.okr.model.AllDetailsTeamModel;
import com.eximbay.okr.model.AllTeamUpdateModel;
import com.eximbay.okr.model.EditForViewAllTeamsModel;
import com.eximbay.okr.model.EditTeamModel;
import com.eximbay.okr.model.TeamListTableModel;
import com.eximbay.okr.model.TeamUpdateFormModel;
import com.eximbay.okr.model.team.TeamAddModel;
import com.eximbay.okr.model.team.TeamViewOkrModel;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ITeamService extends IService<TeamDto, Integer> {

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
