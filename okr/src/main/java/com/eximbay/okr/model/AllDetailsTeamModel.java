package com.eximbay.okr.model;

import com.eximbay.okr.constant.Subheader;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class AllDetailsTeamModel {
    private String subheader = Subheader.TEAMS;
    private Page<TeamForAllDetailsTeamModel> teamsPage;
    private List<Long> navigationPageNumbers;
    private String mutedHeader;
}
