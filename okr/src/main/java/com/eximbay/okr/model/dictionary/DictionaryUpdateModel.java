package com.eximbay.okr.model.dictionary;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DictionaryUpdateModel {

    @NotNull
    private Integer dictionarySeq;

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
    
}
