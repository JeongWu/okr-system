package com.eximbay.okr.model.dictionary;

import com.eximbay.okr.constant.FlagOption;

import lombok.Data;

@Data
public class DictionaryViewModel {

    private Integer dictionarySeq;
    private String dictionaryType;
    private String dictionaryTypeCodeName;
    private String jobType;
    private String jobTypeCodeName;
    private String sentence;
    private String categoryGroup;
    private String categoryGroupCodeName;
    private String category;
    private String categoryCodeName;
    private String taskType;
    private String taskTypeCodeName;
    private String taskMetric;
    private String taskMetricCodeName;
    private String taskIndicator;
    private String taskIndicatorCodeName;
    private String description;
    private String useFlag = FlagOption.Y;
    
}
