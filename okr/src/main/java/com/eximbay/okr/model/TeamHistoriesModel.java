package com.eximbay.okr.model;

import com.eximbay.okr.constant.Subheader;
import lombok.Data;

@Data
public class TeamHistoriesModel {
    private String subheader = Subheader.TEAM_HISTORY;
    private String mutedHeader;
}
