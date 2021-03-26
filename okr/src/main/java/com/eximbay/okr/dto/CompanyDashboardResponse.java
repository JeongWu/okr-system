package com.eximbay.okr.dto;

import com.eximbay.okr.dto.objective.ObjectiveDto;
import lombok.Data;

import java.util.List;

@Data
public class CompanyDashboardResponse {
    private List<ObjectiveDto> companyObjectives;
}
