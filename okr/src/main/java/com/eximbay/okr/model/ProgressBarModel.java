package com.eximbay.okr.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProgressBarModel {
    private String name;
    private Double progress;
    private Double thisWeekProgress;
}
