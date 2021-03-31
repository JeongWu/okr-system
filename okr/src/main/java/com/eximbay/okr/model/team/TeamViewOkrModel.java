package com.eximbay.okr.model.team;

import com.eximbay.okr.constant.Subheader;
import com.eximbay.okr.dto.team.TeamDto;
import com.eximbay.okr.model.objective.ObjectiveViewOkrModel;
import lombok.Data;

import java.util.List;

@Data
public class TeamViewOkrModel {

    private String subheader = Subheader.VIEW_OKR;
    private String mutedHeader = "Team name";
    private List<ObjectiveViewOkrModel> objectives;
    private boolean editable;
    private String quarter;
    private TeamDto team;
}
