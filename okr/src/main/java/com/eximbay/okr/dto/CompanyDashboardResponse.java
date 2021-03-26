package com.eximbay.okr.dto;

import lombok.Data;

import java.util.List;

@Data
public class CompanyDashboardResponse {
    private List<ObjectiveDto> companyObjectives;
}
