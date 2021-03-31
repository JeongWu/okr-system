package com.eximbay.okr.model.evaluationschedule;

import java.util.List;

import com.eximbay.okr.constant.Subheader;
import com.eximbay.okr.dto.codelist.CodeListDto;

import lombok.Data;
@Data
public class EvaluationScheduleModel {
    private String subheader = Subheader.PERFORMANCE_EVALUATION;
    private String mutedHeader = Subheader.SCHEDULE;
    private List<CodeListDto> evaluationTypes;
}
