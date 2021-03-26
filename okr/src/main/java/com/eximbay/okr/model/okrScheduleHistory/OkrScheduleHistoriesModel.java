package com.eximbay.okr.model.okrScheduleHistory;

import com.eximbay.okr.constant.Subheader;
import lombok.Data;

@Data
public class OkrScheduleHistoriesModel {
    private String subheader = Subheader.OKR_SCHEDULE_HISTORY;
    private String mutedHeader;
}
