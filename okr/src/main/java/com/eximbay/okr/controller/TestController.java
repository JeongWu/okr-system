package com.eximbay.okr.controller;

import com.eximbay.okr.service.Interface.IObjectiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    IObjectiveService objectiveService;

    @GetMapping()
    @ResponseBody
    public Object test1(){
        return objectiveService.findAllInUse().toString();
    }
}
