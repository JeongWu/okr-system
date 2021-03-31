package com.eximbay.okr.controller;

import com.eximbay.okr.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {
    private final TemplateService templateService;

    @GetMapping
    public String getTest(){
        Map<String, Object> variables = Map.of("name","Peter", "position", "Java Developer");
        return templateService.buildTemplate(variables, "test");
    }

}
