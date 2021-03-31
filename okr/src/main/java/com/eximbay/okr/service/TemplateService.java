package com.eximbay.okr.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class TemplateService {

    private final SpringTemplateEngine templateEngine;

    public String buildTemplate(Map<String, Object> variables, String path) {
        Context context = new Context();
        context.setVariables(variables);
        return templateEngine.process(path, context);
    }
}
