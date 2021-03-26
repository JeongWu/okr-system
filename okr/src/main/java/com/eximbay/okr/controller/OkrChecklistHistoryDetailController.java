package com.eximbay.okr.controller;

import java.util.ArrayList;
import java.util.List;

import com.eximbay.okr.dto.OkrCheckListDetailDto;
import com.eximbay.okr.dto.KeyResultDto;
import com.eximbay.okr.dto.okrChecklistGroup.OkrChecklistGroupDto;
import com.eximbay.okr.entity.AssessmentCriteria;
import com.eximbay.okr.enumeration.CheckListType;
import com.eximbay.okr.model.CheckListDetail;
import com.eximbay.okr.repository.AssessmentCriteriaRepository;
import com.eximbay.okr.service.OkrCheckListGroupDetailServiceImpl;
import com.eximbay.okr.service.OkrChekListDetailServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/checklistdetail")
public class OkrChecklistHistoryDetailController {

    private final OkrCheckListGroupDetailServiceImpl objectChecklistGroupService;
    private final OkrChekListDetailServiceImpl okrChekListDetailServiceImpl;
    private final AssessmentCriteriaRepository assesmentCriteriaRepository;

    @GetMapping("{id}")
    public String checklistDetailView(Model model, @PathVariable("id") Integer id){
        OkrChecklistGroupDto checkListGroup = objectChecklistGroupService.findById(id).orElseThrow(() -> new NullPointerException("Null"));
        List<OkrCheckListDetailDto> checkListDetail = okrChekListDetailServiceImpl.findByOkrChecklistGroup(checkListGroup);
        CheckListDetail CheckListDetailModel = new CheckListDetail();
        int objectCheckListValue = 0;
        List<AssessmentCriteria> assess = assesmentCriteriaRepository.findByGroupCode("CRITERIA1");
        List<KeyResultDto> keys = new ArrayList<>();
        List<KeyResultDto> keyResultDetail = new ArrayList<>();

        for (int i=0; i<checkListDetail.size(); i++){
            keys.add(checkListDetail.get(i).getKeyResult());
            for (AssessmentCriteria item : assess){
                if (checkListDetail.get(i).getAnswerCode().equals(item.getCode()) 
                && checkListDetail.get(i).getCheckList().getType().equals(CheckListType.OBJECTIVE)){
                    objectCheckListValue += item.getCodeValue();
                }
            }       
        } 

        for (KeyResultDto item : keys) {
            if (!keyResultDetail.contains(item)){
                keyResultDetail.add(item);
            }
        }

        for (int i=0; i<checkListDetail.size(); i++){
            for (AssessmentCriteria item : assess){
                if (checkListDetail.get(i).getCheckList().getType().equals(CheckListType.KEYRESULT)){
                    if (checkListDetail.get(i).getAnswerCode().equals(item.getCode())){
                        int index = checkListDetail.get(i).getKeyResult().getKeyResultSeq();
                        int value = item.getCodeValue();
                for (KeyResultDto keyResult : keyResultDetail) {
                        if (keyResult.getKeyResultSeq() == index){
                            keyResult.setTotalValue(value + keyResult.getTotalValue());
            }
        }  }   
                }
            }       
        } 

        model.addAttribute("model", CheckListDetailModel);
        model.addAttribute("information", checkListGroup);
        model.addAttribute("checkListDetail", checkListDetail);
        model.addAttribute("keyResultDetail", keyResultDetail);
        model.addAttribute("assess", assess);
        model.addAttribute("objectValue", objectCheckListValue);
        return "pages/checklists/checklist-history-detail";
    }    
}
