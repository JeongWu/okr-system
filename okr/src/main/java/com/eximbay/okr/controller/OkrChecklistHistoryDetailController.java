package com.eximbay.okr.controller;

import com.eximbay.okr.constant.Subheader;
import com.eximbay.okr.dto.evaluationcriteria.EvaluationCriteriaDto;
import com.eximbay.okr.dto.checklist.OkrCheckListDetailDto;
import com.eximbay.okr.dto.keyresult.KeyResultDto;
import com.eximbay.okr.dto.objective.ObjectiveDto;
import com.eximbay.okr.dto.okrchecklistgroup.OkrChecklistGroupDto;
import com.eximbay.okr.enumeration.CheckListType;
import com.eximbay.okr.service.Interface.IEvaluationCriteriaService;
import com.eximbay.okr.service.Interface.IObjectiveService;
import com.eximbay.okr.service.Interface.IOkrCheckListDetailService;
import com.eximbay.okr.service.Interface.IOkrCheckListGroupDetailService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/checklist-details")
public class OkrChecklistHistoryDetailController {

    private final IOkrCheckListGroupDetailService objectChecklistGroupService;
    private final IOkrCheckListDetailService okrChekListDetailService;
    private final IEvaluationCriteriaService evaluationCriteriaService;
    private final IObjectiveService objectiveService;

    @GetMapping("{id}")
    public String checklistDetailView(Model model, @PathVariable("id") Integer id){
        OkrChecklistGroupDto checkListGroup = objectChecklistGroupService.findById(id).orElseThrow(() -> new NullPointerException("Null"));
        List<OkrCheckListDetailDto> checkListDetail = okrChekListDetailService.findByOkrChecklistGroup(checkListGroup);
        List<EvaluationCriteriaDto> assess = evaluationCriteriaService.findByGroupCode("CRITERIA1");
        List<KeyResultDto> keys = new ArrayList<>();
        List<KeyResultDto> keyResultDetail = new ArrayList<>();

        for (int i=0; i<checkListDetail.size(); i++){
            keys.add(checkListDetail.get(i).getKeyResult()); 
        } 

        for (KeyResultDto item : keys) {
            if (!keyResultDetail.contains(item)){
                keyResultDetail.add(item);
            }
        }

        for (int i=0; i<checkListDetail.size(); i++){
            for (EvaluationCriteriaDto item : assess){
                if (checkListDetail.get(i).getCheckList().getType().equals(CheckListType.KEYRESULT)){
                    if (checkListDetail.get(i).getAnswerCode().equals(item.getCode())){
                        int index = checkListDetail.get(i).getKeyResult().getKeyResultSeq();
                        int value = item.getCodeValue().intValue();
                for (KeyResultDto keyResult : keyResultDetail) {
                        if (keyResult.getKeyResultSeq() == index){
                            keyResult.setTotalValue(value + keyResult.getTotalValue());
            }
        }  }   
                }
            }       
        } 

        model.addAttribute("subheader", Subheader.OKR_CHECKLIST_GROUP);
        model.addAttribute("information", checkListGroup);
        model.addAttribute("checkListDetail", checkListDetail);
        model.addAttribute("keyResultDetail", keyResultDetail);
        model.addAttribute("assess", assess);
        return "pages/checklists/checklist-history-detail";
    }  
    
    @GetMapping("/new/{id}")
    public String checklistNewView(Model model, @PathVariable("id") Integer id){
        ObjectiveDto objectiveDto = objectiveService.findById(id).orElseThrow(() -> new NullPointerException("Null"));
        OkrChecklistGroupDto checkListGroup = objectChecklistGroupService.findLatestGroupDto(objectiveDto);
        List<OkrCheckListDetailDto> checkListDetail = okrChekListDetailService.findByOkrChecklistGroup(checkListGroup);
        List<EvaluationCriteriaDto> assess = evaluationCriteriaService.findByGroupCode("CRITERIA1");
        List<KeyResultDto> keys = new ArrayList<>();
        List<KeyResultDto> keyResultDetail = new ArrayList<>();

        for (int i=0; i<checkListDetail.size(); i++){
            keys.add(checkListDetail.get(i).getKeyResult());    
        } 

        for (KeyResultDto item : keys) {
            if (!keyResultDetail.contains(item)){
                keyResultDetail.add(item);
            }
        }

        model.addAttribute("subheader", Subheader.OKR_CHECKLIST);
        model.addAttribute("information", checkListGroup);
        model.addAttribute("checkListDetail", checkListDetail);
        model.addAttribute("keyResultDetail", keyResultDetail);
        model.addAttribute("assess", assess);
        return "pages/checklists/checklist-new-detail";
    }

    @PostMapping("/update")
    @ResponseBody
    public JSONArray updated(@RequestParam String updatedForm){
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray = new JSONArray();
        try {
            jsonArray = (JSONArray) jsonParser.parse(updatedForm);
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject object = (JSONObject) jsonArray.get(i);
                String detailSeq = (String)object.get("detailSeq");
                String answerCode =(String) object.get("answerCode");
                OkrCheckListDetailDto dto = okrChekListDetailService.findById(Integer.parseInt(detailSeq)).orElseThrow(() -> new NullPointerException("Null"));
                dto.setAnswerCode(answerCode);
                okrChekListDetailService.save(dto);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }


    @GetMapping("/latest/{id}")
    public String checklistLatestDetailView(Model model, @PathVariable("id") Integer id){
        ObjectiveDto objectiveDto = objectiveService.findById(id).orElseThrow(() -> new NullPointerException("Null"));
        OkrChecklistGroupDto checkListGroup = objectChecklistGroupService.findLatestGroupDto(objectiveDto);
        List<OkrCheckListDetailDto> checkListDetail = okrChekListDetailService.findByOkrChecklistGroup(checkListGroup);
        int objectCheckListValue = 0;
        List<EvaluationCriteriaDto> assess = evaluationCriteriaService.findByGroupCode("CRITERIA1");
        List<KeyResultDto> keys = new ArrayList<>();
        List<KeyResultDto> keyResultDetail = new ArrayList<>();

        for (int i=0; i<checkListDetail.size(); i++){
            keys.add(checkListDetail.get(i).getKeyResult());
            for (EvaluationCriteriaDto item : assess){
                if (checkListDetail.get(i).getAnswerCode().equals(item.getCode()) 
                && checkListDetail.get(i).getCheckList().getType().equals(CheckListType.OBJECTIVE)){
                    objectCheckListValue += item.getCodeValue().intValue();;
                }
            }       
        } 

        for (KeyResultDto item : keys) {
            if (!keyResultDetail.contains(item)){
                keyResultDetail.add(item);
            }
        }
        for (int i=0; i<checkListDetail.size(); i++){
            for (EvaluationCriteriaDto item : assess){
                if (checkListDetail.get(i).getCheckList().getType().equals(CheckListType.KEYRESULT)){
                    if (checkListDetail.get(i).getAnswerCode().equals(item.getCode())){
                        int index = checkListDetail.get(i).getKeyResult().getKeyResultSeq();
                        int value = item.getCodeValue().intValue();;
                for (KeyResultDto keyResult : keyResultDetail) {
                        if (keyResult.getKeyResultSeq() == index){
                            keyResult.setTotalValue(value + keyResult.getTotalValue());
                        }
                    }}
                }
            }
        } 

        int[] keyTotalScore = {0,0,0,0,0};
        for (int i=0; i<keyResultDetail.size(); i++){
            keyTotalScore[i] = keyResultDetail.get(i).getTotalValue();
        }

        checkListGroup.setObjectiveScore(objectCheckListValue);
        checkListGroup.setKeyResult1Score(keyTotalScore[0]);
        checkListGroup.setKeyResult2Score(keyTotalScore[1]);
        checkListGroup.setKeyResult3Score(keyTotalScore[2]);
        checkListGroup.setKeyResult4Score(keyTotalScore[3]);
        checkListGroup.setKeyResult5Score(keyTotalScore[4]);
        objectChecklistGroupService.save(checkListGroup);

        model.addAttribute("subheader", Subheader.OKR_CHECKLIST);
        model.addAttribute("information", checkListGroup);
        model.addAttribute("checkListDetail", checkListDetail);
        model.addAttribute("keyResultDetail", keyResultDetail);
        model.addAttribute("assess", assess);
        return "pages/checklists/checklist-latest-detail";
    }
}
