package com.eximbay.okr.model.dictionary;

import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class DictionaryAddModel {

    // private Integer dictionarySeq;

    @NotNull
    private String dictionaryType;
    @NotNull
    private String jobType;
    @NotNull
    private String sentence;

    private String categoryGroup;

    private String category;

    @NotNull
    private String taskType;

    private String taskMetric;

    private String taskIndicator;
    
    private String description;
    
    @NotNull
    private boolean useFlag;
    private String action;
    
}
