package com.eximbay.okr.api;

import com.eximbay.okr.constant.DictionaryType;
import com.eximbay.okr.entity.CodeList;

import com.eximbay.okr.entity.Dictionary;
import com.eximbay.okr.model.dictionary.DictionaryViewModel;
import com.eximbay.okr.model.dictionary.SelectTypeModel;
import com.eximbay.okr.repository.DictionaryRepository;
import com.eximbay.okr.service.Interface.IDictionaryService;
import com.eximbay.okr.service.specification.DictionaryQuery;

import lombok.AllArgsConstructor;
import ma.glasnost.orika.MapperFacade;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/dictionary")
@AllArgsConstructor
public class DictionaryAPI {

        private final DictionaryRepository dictionaryRepository;
        private final IDictionaryService dictionaryService;
        private final MapperFacade mapper;
        private final DictionaryQuery dictionaryQuery;

        @PostMapping("/datatables")
        public List<DictionaryViewModel> getDictionaryData() {
                List<Dictionary> dictionaries = dictionaryRepository.findAll();

                List<DictionaryViewModel> viewModels = mapper.mapAsList(dictionaries, DictionaryViewModel.class);
                SelectTypeModel selectTypeModel = dictionaryService.buildSelectTypeModel();

                for (DictionaryViewModel model : viewModels) {
                        Optional<CodeList> dictionaryType = selectTypeModel.getDictionaryType().stream()
                                        .filter(d -> d.getCodeListId().getCode().equals(model.getDictionaryType()))
                                        .findFirst();
                        model.setDictionaryTypeCodeName(dictionaryType.get().getCodeName());

                        Optional<CodeList> jobType = selectTypeModel.getJobType().stream()
                                        .filter(d -> d.getCodeListId().getCode().equals(model.getJobType()))
                                        .findFirst();
                        model.setJobTypeCodeName(jobType.get().getCodeName());

                        if (model.getDictionaryType().equals(DictionaryType.COMPETENCE)) {
                                Optional<CodeList> categoryGroup = selectTypeModel.getCategoryGroup().stream().filter(
                                                d -> d.getCodeListId().getCode().equals(model.getCategoryGroup()))
                                                .findFirst();
                                model.setCategoryGroupCodeName(categoryGroup.get().getCodeName());

                                Optional<CodeList> category = selectTypeModel.getCategory().stream()
                                                .filter(d -> d.getCodeListId().getCode().equals(model.getCategory()))
                                                .findFirst();
                                model.setCategoryCodeName(category.get().getCodeName());

                        }

                }

                return viewModels;
        }

        @RequestMapping("/keyResult/datatables")
        public List<DictionaryViewModel> getDictionaryOfKeyResult() {

                List<Dictionary> dictionaries = dictionaryRepository
                                .findAll(dictionaryQuery.findByDictionaryType(DictionaryType.KEY_RESULT)
                                                .and(dictionaryQuery.findActiveDictionaryOnly()));

                List<DictionaryViewModel> viewModels = mapper.mapAsList(dictionaries, DictionaryViewModel.class);
                SelectTypeModel selectTypeModel = dictionaryService.buildSelectTypeModel();

                for (DictionaryViewModel model : viewModels) {
                        Optional<CodeList> availableJobType = selectTypeModel.getJobType().stream()
                                        .filter(d -> d.getCodeListId().getCode().equals(model.getJobType()))
                                        .findFirst();
                        model.setJobTypeCodeName(availableJobType.get().getCodeName());
                        Optional<CodeList> availableTaskType = selectTypeModel.getTaskType().stream()
                                        .filter(d -> d.getCodeListId().getCode().equals(model.getTaskType()))
                                        .findFirst();
                        model.setTaskTypeCodeName(availableTaskType.get().getCodeName());
                        Optional<CodeList> availableTaskMetric = selectTypeModel.getTaskMetric().stream()
                                        .filter(d -> d.getCodeListId().getCode().equals(model.getTaskMetric()))
                                        .findFirst();
                        model.setTaskMetricCodeName(availableTaskMetric.get().getCodeName());
                        Optional<CodeList> availableTaskIndicator = selectTypeModel.getTaskIndicator().stream()
                                        .filter(d -> d.getCodeListId().getCode().equals(model.getTaskIndicator()))
                                        .findFirst();
                        model.setTaskIndicatorCodeName(availableTaskIndicator.get().getCodeName());
                }

                return viewModels;
        }
}
