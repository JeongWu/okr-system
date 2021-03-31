package com.eximbay.okr.api;

import com.eximbay.okr.dto.checklist.CheckListDto;
import com.eximbay.okr.service.CheckListServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/checklists")
public class ChecklistAPI {

    private final CheckListServiceImpl checkListService;

    @PostMapping("/datatables")
    List<CheckListDto> checklistDataTable(){
    List<CheckListDto> checkLists = checkListService.findAll();
    return checkLists;
}
    
}
