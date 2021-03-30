package com.eximbay.okr.api;

import java.util.List;
import java.util.Optional;

import com.eximbay.okr.constant.DictionaryType;
import com.eximbay.okr.dto.CodeListDto;
import com.eximbay.okr.entity.Dictionary;
import com.eximbay.okr.model.dictionary.DictionaryViewModel;
import com.eximbay.okr.model.dictionary.SelectTypeModel;
import com.eximbay.okr.repository.DictionaryRepository;
import com.eximbay.okr.service.Interface.IDictionaryService;
import com.eximbay.okr.service.specification.DictionaryQuery;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import ma.glasnost.orika.MapperFacade;

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
                        Optional<CodeListDto> dictionaryType = selectTypeModel.getDictionaryTypes().stream()
                                        .filter(d -> d.getCodeListId().getCode().equals(model.getDictionaryType()))
                                        .findFirst();
                        model.setDictionaryTypeCodeName(dictionaryType.get().getCodeName());

                        Optional<CodeListDto> jobType = selectTypeModel.getJobTypes().stream()
                                        .filter(d -> d.getCodeListId().getCode().equals(model.getJobType()))
                                        .findFirst();
                        model.setJobTypeCodeName(jobType.get().getCodeName());

                        if (model.getDictionaryType().equals(DictionaryType.COMPETENCE)) {
                                Optional<CodeListDto> categoryGroup = selectTypeModel.getCategoryGroups().stream().filter(
                                                d -> d.getCodeListId().getCode().equals(model.getCategoryGroup()))
                                                .findFirst();
                                model.setCategoryGroupCodeName(categoryGroup.get().getCodeName());

                                Optional<CodeListDto> category = selectTypeModel.getCategories().stream()
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
                        Optional<CodeListDto> availableJobType = selectTypeModel.getJobTypes().stream()
                                        .filter(d -> d.getCodeListId().getCode().equals(model.getJobType()))
                                        .findFirst();
                        model.setJobTypeCodeName(availableJobType.get().getCodeName());
                        Optional<CodeListDto> availableTaskType = selectTypeModel.getTaskTypes().stream()
                                        .filter(d -> d.getCodeListId().getCode().equals(model.getTaskType()))
                                        .findFirst();
                        model.setTaskTypeCodeName(availableTaskType.get().getCodeName());
                        Optional<CodeListDto> availableTaskMetric = selectTypeModel.getTaskMetrics().stream()
                                        .filter(d -> d.getCodeListId().getCode().equals(model.getTaskMetric()))
                                        .findFirst();
                        model.setTaskMetricCodeName(availableTaskMetric.get().getCodeName());
                        Optional<CodeListDto> availableTaskIndicator = selectTypeModel.getTaskIndicators().stream()
                                        .filter(d -> d.getCodeListId().getCode().equals(model.getTaskIndicator()))
                                        .findFirst();
                        model.setTaskIndicatorCodeName(availableTaskIndicator.get().getCodeName());
                }

                return viewModels;
        }
}
