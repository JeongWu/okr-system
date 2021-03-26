package com.eximbay.okr.api;

import java.util.List;

import com.eximbay.okr.dto.MemberDto;
import com.eximbay.okr.dto.TeamMemberDto;
import com.eximbay.okr.service.MemberServiceImpl;
import com.eximbay.okr.service.TeamMemberServiceImpl;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/members")
@AllArgsConstructor
public class MemberAPI {

    private final MemberServiceImpl memberService;
    private final TeamMemberServiceImpl teamMemberService;
    

    @PostMapping("/datatables")
    public List<MemberDto> getMemberData() {
        List<MemberDto> teamMember = memberService.findAll();
        return teamMember;
    }

    @PostMapping("/belong/datatables/{memberSeq}")
    public List<TeamMemberDto> getMemberData1(@PathVariable("memberSeq") Integer memberSeq){
        MemberDto dto = memberService.findById(memberSeq)
                .orElseThrow(()-> new NullPointerException("Null"));
        List<TeamMemberDto> teams = teamMemberService.findSearchBelong(dto);       
        return teams;
    }
}
