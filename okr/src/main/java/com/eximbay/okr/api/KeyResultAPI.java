package com.eximbay.okr.api;

import com.eximbay.okr.model.okr.OkrKeyResultDetailModel;
import com.eximbay.okr.service.Interface.IOkrService;
import com.eximbay.okr.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/key-results")
public class KeyResultAPI {

    private final TemplateService templateService;
    private final IOkrService okrService;

    @GetMapping("/okrs/quarterly")
    public String viewOkrDetail(String type, Integer seq){

        OkrKeyResultDetailModel viewModel = okrService.buildOkrKeyResultDetailModel(type, seq);
        Map<String, Object> variables = Map.of("model", viewModel);
        return templateService.buildTemplate(variables, "pages/okrs/okr-key-result-details");
    }
}
