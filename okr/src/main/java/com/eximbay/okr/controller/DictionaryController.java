package com.eximbay.okr.controller;

import com.eximbay.okr.constant.Subheader;
import com.eximbay.okr.model.dictionary.DictionaryAddModel;
import com.eximbay.okr.model.dictionary.DictionaryUpdateModel;
import com.eximbay.okr.model.dictionary.SelectTypeModel;
import com.eximbay.okr.service.Interface.IDictionaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dictionary")
public class DictionaryController {

    private final IDictionaryService dictionaryService;

    @GetMapping
    public String viewAllDictionary(Model model) {
        SelectTypeModel selectTypeModel = dictionaryService.buildSelectTypeModel();
        model.addAttribute("subheader", Subheader.DICTIONARY);
        model.addAttribute("TypeModel", selectTypeModel);
        return "pages/dictionary/dictionary";
    }

    @GetMapping("/add")
    public String addDictionary(Model model) {
        model.addAttribute("subheader", "Add Dictionary");

        SelectTypeModel selectTypeModel = dictionaryService.buildSelectTypeModel();

        model.addAttribute("TypeModel", selectTypeModel);

        DictionaryAddModel dictionaryAddModel = new DictionaryAddModel();
        model.addAttribute("dataModel", dictionaryAddModel);
        return "pages/dictionary/add_dictionary";
    }

    @PostMapping(value = "/add")
    public String addDictionary(@ModelAttribute DictionaryAddModel dictionaryAddModel) {
        dictionaryService.addDictionary(dictionaryAddModel);
        switch (dictionaryAddModel.getAction()) {
            case "saveAndAddNew":
                return "redirect:/dictionary/add";
            case "saveAndExit":
            default:
                return "redirect:/dictionary";
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model){
        model.addAttribute("subheader", "Edit Dictionary");
        
        SelectTypeModel selectTypeModel = dictionaryService.buildSelectTypeModel();

        model.addAttribute("TypeModel", selectTypeModel);

        DictionaryUpdateModel dictionaryUpdateModel = dictionaryService.buildUpdateDictionaryModel(id);
        model.addAttribute("dataModel", dictionaryUpdateModel);
        return "pages/dictionary/edit_dictionary";
    }

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String saveDictionary(@Validated DictionaryUpdateModel updateFormModel, BindingResult error){
        if (error.hasErrors()) return "redirect:/dictionary/edit/"+ updateFormModel.getDictionarySeq();
        dictionaryService.updateFormModel(updateFormModel);
        return "redirect:/dictionary";
    }

}
