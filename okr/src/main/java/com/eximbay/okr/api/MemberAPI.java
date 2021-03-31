package com.eximbay.okr.api;

import com.eximbay.okr.dto.member.MemberDto;
import com.eximbay.okr.dto.teammember.TeamMemberDto;
import com.eximbay.okr.model.member.MemberViewOkrModel;
import com.eximbay.okr.service.MemberServiceImpl;
import com.eximbay.okr.service.TeamMemberServiceImpl;
import com.eximbay.okr.service.TemplateService;
import com.eximbay.okr.utils.DateTimeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/members")
public class MemberAPI {

    private final MemberServiceImpl memberService;
    private final TeamMemberServiceImpl teamMemberService;
    private final TemplateService templateService;

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

    @GetMapping("/okrs/quarterly")
    public String viewOkr(Integer seq, String quarter){
        if (quarter == null || !Pattern.compile("^\\d{4}-\\dQ").matcher(quarter).matches())
            quarter = DateTimeUtils.findCurrentQuarter();

        MemberViewOkrModel viewModel = memberService.buildMemberViewOkrModel(seq, quarter);
        Map<String, Object> variables = Map.of("model", viewModel);
        return templateService.buildTemplate(variables, "pages/members/okr");
    }
}
