package com.eximbay.okr.api;

import com.eximbay.okr.service.CheckListServiceImpl;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import lombok.AllArgsConstructor;

import java.util.List;

import com.eximbay.okr.dto.CheckListDto;

@RestController
@RequestMapping("api/checklists")
@AllArgsConstructor
public class CheklistAPI {

    private final CheckListServiceImpl checkListService;

    @PostMapping("/datatables")
    List<CheckListDto> checklistDataTable(){
    List<CheckListDto> checkLists = checkListService.findAll();
    return checkLists;
}
    
}
