package com.eximbay.okr.controller.advisor;

import com.eximbay.okr.model.WireframeModel;
import com.eximbay.okr.service.Interface.ITeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class WireframeAdvisor {

    @Autowired
    private ITeamService teamService;

    @ModelAttribute("wireframeModel")
    public WireframeModel getWireFrameTeamList() {
        WireframeModel wireframeModel = teamService.buildWireframeModel();
        return wireframeModel;
    }

}
