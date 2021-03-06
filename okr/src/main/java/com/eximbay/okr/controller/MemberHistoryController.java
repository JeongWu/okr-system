package com.eximbay.okr.controller;

import com.eximbay.okr.constant.Subheader;
import com.eximbay.okr.dto.member.MemberDto;
import com.eximbay.okr.model.MemberModel;
import com.eximbay.okr.service.Interface.IMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member-histories")
public class MemberHistoryController {

    private final IMemberService memberService;

    @GetMapping("/{memberSeq}")
    public String getModel(Model model, @PathVariable Integer memberSeq) {
        
        MemberDto dto = memberService.findById(memberSeq)
        .orElseThrow(()-> new NullPointerException("Null"));
        MemberModel historyModel = new MemberModel(Subheader.MEMBER_HISTORY, dto.getName());
        model.addAttribute("model", historyModel);
        model.addAttribute("memberSeq", memberSeq);
        model.addAttribute("member", dto);
        return "pages/members/member_history";
    }    
}