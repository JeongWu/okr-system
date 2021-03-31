package com.eximbay.okr.model;

import java.util.List;

import com.eximbay.okr.constant.Subheader;
import com.eximbay.okr.entity.EvaluationCriteria;

import lombok.Data;

@Data
public class CheckListDetailModel {
    private String subheader = Subheader.OKR_CHECKLIST;
    private List<EvaluationCriteria> EvaluationCriteria;
}
