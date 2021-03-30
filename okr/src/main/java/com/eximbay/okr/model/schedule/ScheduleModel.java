package com.eximbay.okr.model.schedule;

import java.util.List;

import com.eximbay.okr.constant.Subheader;
import com.eximbay.okr.dto.CodeListDto;

import lombok.Data;

@Data
public class ScheduleModel {
    private String subheader = Subheader.PERFORMANCE_EVALUATION;
    private String mutedHeader = Subheader.SCHEDULE;
    private List<CodeListDto> evaluationTypes;
}
