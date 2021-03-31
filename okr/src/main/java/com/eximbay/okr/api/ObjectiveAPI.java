package com.eximbay.okr.api;

import com.eximbay.okr.dto.objective.UpdateObjectivePriorityRequest;
import com.eximbay.okr.model.okr.OkrObjectiveDetailModel;
import com.eximbay.okr.service.Interface.IObjectiveService;
import com.eximbay.okr.service.Interface.IOkrService;
import com.eximbay.okr.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/objectives")
public class ObjectiveAPI {

    private final TemplateService templateService;
    private final IObjectiveService objectiveService;
    private final IOkrService okrService;

    @PostMapping("/update-priority")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void updateObjectivePriority(@RequestBody UpdateObjectivePriorityRequest request){
        objectiveService.updateObjectivePriority(request);
    }

    @GetMapping("/okrs/quarterly")
    public String viewOkrDetail(String type, Integer seq){

        OkrObjectiveDetailModel viewModel = okrService.buildOkrObjectiveDetailModel(type, seq);
        Map<String, Object> variables = Map.of("model", viewModel);
        return templateService.buildTemplate(variables, "pages/okrs/okr-objective-details");
    }
}
