package com.eximbay.okr.api;

import com.eximbay.okr.dto.objective.UpdateObjectivePriorityRequest;
import com.eximbay.okr.service.Interface.IObjectiveService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/objectives")
@Data
@AllArgsConstructor
public class ObjectiveAPI {
    private final IObjectiveService objectiveService;

    @PostMapping("/update-priority")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void updateObjectivePriority(@RequestBody UpdateObjectivePriorityRequest request){
        objectiveService.updateObjectivePriority(request);
    }
}
