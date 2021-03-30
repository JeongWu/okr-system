package com.eximbay.okr.service;

import java.util.List;
import java.util.Optional;

import com.eximbay.okr.constant.FlagOption;
import com.eximbay.okr.constant.GroupCode;
import com.eximbay.okr.dto.CodeListDto;
import com.eximbay.okr.dto.dictionary.DictionaryDto;
import com.eximbay.okr.entity.CodeList;
import com.eximbay.okr.entity.Dictionary;
import com.eximbay.okr.exception.UserException;
import com.eximbay.okr.model.dictionary.DictionaryAddModel;
import com.eximbay.okr.model.dictionary.DictionaryUpdateModel;
import com.eximbay.okr.model.dictionary.SelectTypeModel;
import com.eximbay.okr.repository.CodeListRepository;
import com.eximbay.okr.repository.DictionaryRepository;
import com.eximbay.okr.service.Interface.IDictionaryService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import ma.glasnost.orika.MapperFacade;

@Service
@Transactional
@AllArgsConstructor
public class DictionaryService implements IDictionaryService {
    private final DictionaryRepository dictionaryRepository;
    private final CodeListRepository codeListRepository;
    private final MapperFacade mapper;

    @Override
    public List<DictionaryDto> findAll() {
        List<Dictionary> dictionaries = dictionaryRepository.findAll();
        return mapper.mapAsList(dictionaries, DictionaryDto.class);
    }

    @Override
    public Optional<DictionaryDto> findById(Integer id) {
        Optional<Dictionary> dictionary = dictionaryRepository.findById(id);
        return dictionary.map(m -> mapper.map(m, DictionaryDto.class));
    }

    @Override
    public void remove(DictionaryDto dictionaryDto) {
        Dictionary dictionary = mapper.map(dictionaryDto, Dictionary.class);
        dictionaryRepository.delete(dictionary);
    }

    @Override
    public DictionaryDto save(DictionaryDto dictionaryDto) {
        Dictionary dictionary = mapper.map(dictionaryDto, Dictionary.class);
        dictionary = dictionaryRepository.save(dictionary);
        return mapper.map(dictionary, DictionaryDto.class);
    }

    @Override
    public SelectTypeModel buildSelectTypeModel() {

        SelectTypeModel selectTypeModel = new SelectTypeModel();

        List<CodeList> availableDictinaryType = codeListRepository.findByGroupCodeAndUseFlagOrderBySortOrderAsc(GroupCode.DICTIONARY_TYPE, FlagOption.Y);
        List<CodeList> availableCategory = codeListRepository.findByGroupCodeAndUseFlagOrderBySortOrderAsc(GroupCode.DIC_CATEGORY, FlagOption.Y);
        List<CodeList> availableCategoryGroup = codeListRepository.findByGroupCodeAndUseFlagOrderBySortOrderAsc(GroupCode.DIC_CATEGORY_GROUP, FlagOption.Y);
        List<CodeList> availableJobType = codeListRepository.findByGroupCodeAndUseFlagOrderBySortOrderAsc(GroupCode.JOB_TYPE, FlagOption.Y);
        List<CodeList> availablePosition = codeListRepository.findByGroupCodeAndUseFlagOrderBySortOrderAsc(GroupCode.POSITION, FlagOption.Y);
        List<CodeList> availableTaskType = codeListRepository.findByGroupCodeAndUseFlagOrderBySortOrderAsc(GroupCode.TASK_TYPE, FlagOption.Y);
        List<CodeList> availableTaskMetric = codeListRepository.findByGroupCodeAndUseFlagOrderBySortOrderAsc(GroupCode.TASK_METRIC, FlagOption.Y);
        List<CodeList> avalableTaskIndicator = codeListRepository.findByGroupCodeAndUseFlagOrderBySortOrderAsc(GroupCode.TASK_INDICATOR, FlagOption.Y);

        selectTypeModel.setDictionaryTypes(mapper.mapAsList(availableDictinaryType, CodeListDto.class));
        selectTypeModel.setCategories(mapper.mapAsList(availableCategory, CodeListDto.class));
        selectTypeModel.setCategoryGroups(mapper.mapAsList(availableCategoryGroup, CodeListDto.class));
        selectTypeModel.setJobTypes(mapper.mapAsList(availableJobType, CodeListDto.class));
        selectTypeModel.setPositions(mapper.mapAsList(availablePosition, CodeListDto.class));
        selectTypeModel.setTaskTypes(mapper.mapAsList(availableTaskType, CodeListDto.class));
        selectTypeModel.setTaskMetrics(mapper.mapAsList(availableTaskMetric, CodeListDto.class));
        selectTypeModel.setTaskIndicators(mapper.mapAsList(avalableTaskIndicator, CodeListDto.class));
        
        return selectTypeModel;

    }

    @Override
    public void addDictionary(DictionaryAddModel dictionaryAddModel) {

        Dictionary dictionary = mapper.map(dictionaryAddModel, Dictionary.class);
        if (dictionaryAddModel.isUseFlag()) {
            dictionary.setUseFlag("Y");
        } else {
            dictionary.setUseFlag("N");
        }
        dictionaryRepository.save(dictionary);

    }

    @Override
    public DictionaryUpdateModel buildUpdateDictionaryModel(Integer id) {
        Optional<Dictionary> dictionary = dictionaryRepository.findById(id);
        if (dictionary.isEmpty()) throw new UserException(new NotFoundException("Not found Object with Id = "+ id));

        DictionaryUpdateModel updateModel  = mapper.map(dictionary.get(), DictionaryUpdateModel.class);

        updateModel.setUseFlag(dictionary.get().getUseFlag().equals(FlagOption.Y));

        return updateModel;
    }

    @Override
    public void updateFormModel(DictionaryUpdateModel updateFormModel) {
        Optional<Dictionary> dictionary = dictionaryRepository.findById(updateFormModel.getDictionarySeq());
        if (dictionary.isEmpty()) throw new UserException(new NotFoundException("Not found Object with Id = "+ updateFormModel.getDictionarySeq()));
        mapper.map(updateFormModel, dictionary.get());
        if (updateFormModel.isUseFlag()) dictionary.get().setUseFlag(FlagOption.Y);
        else dictionary.get().setUseFlag(FlagOption.N);
        
        dictionaryRepository.save(dictionary.get());

    }
}
