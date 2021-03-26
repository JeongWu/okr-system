package com.eximbay.okr.controller;

import com.eximbay.okr.dto.DivisionDto;
import com.eximbay.okr.dto.MemberDto;
import com.eximbay.okr.entity.Member;
import com.eximbay.okr.repository.MemberRepository;
import com.eximbay.okr.service.Interface.IDivisionMemberService;
import com.eximbay.okr.service.Interface.IMemberService;
import com.eximbay.okr.utils.MapperUtil;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;



import java.util.List;

import javax.management.modelmbean.ModelMBean;

@Controller
@AllArgsConstructor
public class MemberController {

    @Autowired
    MemberRepository memberRepository;

    @RequestMapping("/member-list")
    public String memberList(Model model){
        // List<Member> members = memberRepository.findAll();
        // long totalCount= members.size();
        // model.addAttribute("totalCount", totalCount);
        return "pages/members/memberList";
    }


    // @ResponseBody
    // @RequestMapping("get-member-data")
    // public List<MemberDto> getMemberData(){
    //     List<Member> members = memberRepository.findAll();
    //     List<MemberDto> teamMember = MapperUtil.mapList(members, MemberDto.class);
    //     return teamMember;
    //     }


    @RequestMapping("/add_member")
    public String addMember(){
        return "pages/members/member_add";
    }
	
	@RequestMapping("/edit-details")
    public String editdetails(){
        return "pages/members/member_edit";
    }
	
	@RequestMapping("/view-history")
    public String viewhistory(){
        return "pages/members/member_history";
    }


}
