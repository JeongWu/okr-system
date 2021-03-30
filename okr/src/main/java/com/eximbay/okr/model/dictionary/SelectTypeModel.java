package com.eximbay.okr.model.dictionary;

import java.util.List;

import com.eximbay.okr.dto.CodeListDto;

import lombok.Data;

@Data
public class SelectTypeModel {

    private List<CodeListDto> dictionaryTypes;
    private List<CodeListDto> categoryGroups;
    private List<CodeListDto> categories;
    private List<CodeListDto> jobTypes;
    private List<CodeListDto> positions;
    private List<CodeListDto> taskIndicators;
    private List<CodeListDto> taskMetrics;
    private List<CodeListDto> taskTypes;
    private List<CodeListDto> teamTypes;
    
}
