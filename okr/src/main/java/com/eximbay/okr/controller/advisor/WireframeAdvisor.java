package com.eximbay.okr.controller.advisor;

import com.eximbay.okr.model.WireframeModel;
import com.eximbay.okr.service.Interface.ICommonService;
import com.eximbay.okr.service.Interface.IMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
@RequiredArgsConstructor
public class WireframeAdvisor {

    private final IMemberService memberService;
    private final ICommonService commonService;

    @ModelAttribute("wireframeModel")
    public WireframeModel getWireFrameTeamList() {
        WireframeModel wireframeModel = commonService.buildWireframeModel();
        wireframeModel.setCurrentMember(memberService.getCurrentMember().orElse(null));
        return wireframeModel;
    }

}
