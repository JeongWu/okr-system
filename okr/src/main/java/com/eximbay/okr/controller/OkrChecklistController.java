package com.eximbay.okr.controller;

import com.eximbay.okr.dto.checklist.CheckListDto;
import com.eximbay.okr.service.Interface.ICheckListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping("/checklists")
public class OkrChecklistController {

    private final ICheckListService checkListService;

    @GetMapping
    public String checklistView(){
        return "pages/checklists/checklist-add";
    }

    @PostMapping("/add")
    public String checkListAdd(CheckListDto dto){
        checkListService.save(dto);
        return "redirect:/checklists";
    }

    @PostMapping("/update")
    @ResponseBody
    public Object CheckListUpdate(String useFlag, Integer checkListSeq){
        CheckListDto checklist = checkListService.findById(checkListSeq)
        .orElseThrow(()-> new NullPointerException("Null"));
        checklist.setUseFlag(useFlag);
        checkListService.save(checklist);
        return checklist;
    }   
}
