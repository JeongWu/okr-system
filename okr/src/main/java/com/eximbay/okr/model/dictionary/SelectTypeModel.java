package com.eximbay.okr.model.dictionary;

import java.util.List;

import com.eximbay.okr.entity.CodeList;

import lombok.Data;

@Data
public class SelectTypeModel {

    private List<CodeList> dictionaryType;

    private List<CodeList> categoryGroup;

    private List<CodeList> category;

    private List<CodeList> jobType;

    private List<CodeList> position;

    private List<CodeList> taskIndicator;
    
    private List<CodeList> taskMetric;

    private List<CodeList> taskType;

    private List<CodeList> teamType;
    
}
