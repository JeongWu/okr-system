package com.eximbay.okr.dto.okr;

import lombok.Data;

import java.util.List;

@Data
public class ObjectiveKeyResultsDto {
    private int year;
    private int quarter;
    private String objective;
    private List<KeyResultDto> key_results;
}
