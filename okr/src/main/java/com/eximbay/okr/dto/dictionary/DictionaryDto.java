package com.eximbay.okr.dto.dictionary;

import com.eximbay.okr.constant.FlagOption;
import lombok.Data;

@Data
public class DictionaryDto {

    private Integer dictionarySeq;
    private String dictionaryType;
    private String jobType;
    private String sentence;
    private String categoryGroup;
    private String category;
    private String taskType;
    private String taskMetric;
    private String taskIndicator;
    private String description;
    private String useFlag = FlagOption.Y;
}
