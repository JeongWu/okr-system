package com.eximbay.okr.dto.objective;

import lombok.Data;

import java.util.List;

@Data
public class UpdateObjectivePriorityRequest {
    private List<UpdateObjectivePriorityDto> data;
}
