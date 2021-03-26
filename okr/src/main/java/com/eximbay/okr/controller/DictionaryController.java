package com.eximbay.okr.controller;

import java.util.List;


import com.eximbay.okr.entity.Dictionary;
import com.eximbay.okr.model.dictionary.AllCategoryGroupModel;
import com.eximbay.okr.model.dictionary.AllCategoryModel;
import com.eximbay.okr.model.dictionary.AllDictionaryTypeModel;
import com.eximbay.okr.model.dictionary.AllJobTypeModel;
import com.eximbay.okr.model.dictionary.DictionaryAddModel;
import com.eximbay.okr.model.dictionary.DictionaryUpdateModel;
import com.eximbay.okr.model.dictionary.SelectTypeModel;
import com.eximbay.okr.repository.DictionaryRepository;
import com.eximbay.okr.service.Interface.IDictionaryService;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.eximbay.okr.utils.MapperUtil;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/dictionary")
public class DictionaryController {

    private final IDictionaryService dictionaryService;

    @Autowired
    private DictionaryRepository dictionaryRepository;

    @GetMapping
    public String viewAllDictionary(Model model) {

         //To get Dictionary data for search option
         List<Dictionary> dictionaries = dictionaryRepository.findAll();
         List<AllDictionaryTypeModel> dictionaryTypes = MapperUtil.mapList(dictionaries, AllDictionaryTypeModel.class);
         List<AllJobTypeModel> jobTypeModels = MapperUtil.mapList(dictionaries, AllJobTypeModel.class);
         List<AllCategoryGroupModel> categoryGroupModels = MapperUtil.mapList(dictionaries, AllCategoryGroupModel.class);
         List<AllCategoryModel> categoryModels = MapperUtil.mapList(dictionaries, AllCategoryModel.class);
 
         model.addAttribute("dictionaryTypes", dictionaryTypes);
         model.addAttribute("jobTypeModels", jobTypeModels);
         model.addAttribute("categoryGroupModels", categoryGroupModels);
         model.addAttribute("categoryModels", categoryModels);
 

        return "pages/dictionary/dictionary_list";
    }

    @GetMapping("/keyresult")
    public String viewAllKeyResult(Model model) {

        SelectTypeModel selectTypeModel = dictionaryService.buildSelectTypeModel();

        model.addAttribute("TypeModel", selectTypeModel);
     
        return "pages/dictionary/keyresult_list";
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
                return "redirect:/dictionary";
            default:
                return "redirect:/dictionary";
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        model.addAttribute("subheader", "Edit Dictionary");

        SelectTypeModel selectTypeModel = dictionaryService.buildSelectTypeModel();

        model.addAttribute("TypeModel", selectTypeModel);

        DictionaryUpdateModel dictionaryUpdateModel = dictionaryService.buildUpdateDictionaryModel(id);
        model.addAttribute("dataModel", dictionaryUpdateModel);
        return "pages/dictionary/edit_dictionary";
    }

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String saveDictionary(@Validated DictionaryUpdateModel updateFormModel, BindingResult error) {
        if (error.hasErrors())
            return "redirect:/dictionary/edit/" + updateFormModel.getDictionarySeq();
        dictionaryService.updateFormModel(updateFormModel);
        return "redirect:/dictionary";
    }

}
