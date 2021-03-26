package com.eximbay.okr.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.eximbay.okr.constant.FlagOption;
import com.eximbay.okr.dto.CodeGroupDto;
import com.eximbay.okr.dto.dictionary.DictionaryDto;
import com.eximbay.okr.entity.Dictionary;
import com.eximbay.okr.exception.UserException;
import com.eximbay.okr.model.dictionary.DictionaryAddModel;
import com.eximbay.okr.model.dictionary.DictionaryUpdateModel;
import com.eximbay.okr.model.dictionary.SelectTypeModel;
import com.eximbay.okr.repository.DictionaryRepository;
import com.eximbay.okr.service.Interface.ICodeGroupService;
import com.eximbay.okr.service.Interface.IDictionaryService;
import org.springframework.transaction.annotation.Transactional;

import javassist.NotFoundException;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import ma.glasnost.orika.MapperFacade;

@Service
@Transactional
@AllArgsConstructor
public class DictionaryService implements IDictionaryService {
    private final DictionaryRepository dictionaryRepository;
    private final MapperFacade mapper;
    private final ICodeGroupService codeGroupService;

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

        Optional<CodeGroupDto> dictionaryTypeDto = codeGroupService.findByGroupCode("DICTIONARY_TYPE");
        // dictionaryTypeDto.get().getCodeLists().stream().filter(m->m.getUseFlag().equals(FlagOption.Y)).collect(Collectors.toList());
        selectTypeModel.setDictionaryType(dictionaryTypeDto.get().getCodeLists().stream().filter(m->m.getUseFlag().equals(FlagOption.Y)).collect(Collectors.toList()));

        Optional<CodeGroupDto> dicCategoryDto = codeGroupService.findByGroupCode("DIC_CATEGORY");
        selectTypeModel.setCategory(dicCategoryDto.get().getCodeLists().stream().filter(m->m.getUseFlag().equals(FlagOption.Y)).collect(Collectors.toList()));

        Optional<CodeGroupDto> dicCategoryGroupDto = codeGroupService.findByGroupCode("DIC_CATEGORY_GROUP");
        selectTypeModel.setCategoryGroup(dicCategoryGroupDto.get().getCodeLists().stream().filter(m->m.getUseFlag().equals(FlagOption.Y)).collect(Collectors.toList()));

        Optional<CodeGroupDto> jobTypeDto = codeGroupService.findByGroupCode("JOB_TYPE");
    
        selectTypeModel.setJobType(jobTypeDto.get().getCodeLists().stream().filter(m->m.getUseFlag().equals(FlagOption.Y)).collect(Collectors.toList()));
 
        // Optional<CodeGroupDto> objectiveLevelDto=
        // codeGroupService.findByGroupCode("OBJECTIVE_LEVEL");
        // selectTypeModel.setObjectiveLevel(objectiveLevelDto.get().getCodeLists());

        // Optional<CodeGroupDto> objectiveTypeDto=
        // codeGroupService.findByGroupCode("OBJECTIVE_TYPE");
        // selectTypeModel.setObjectiveType(objectiveTypeDto.get().getCodeLists());

        Optional<CodeGroupDto> positionDto = codeGroupService.findByGroupCode("POSITION");
        selectTypeModel.setPosition(positionDto.get().getCodeLists().stream().filter(m->m.getUseFlag().equals(FlagOption.Y)).collect(Collectors.toList()));

        Optional<CodeGroupDto> taskIndicatorDto = codeGroupService.findByGroupCode("TASK_INDICATOR");
        selectTypeModel.setTaskIndicator(taskIndicatorDto.get().getCodeLists().stream().filter(m->m.getUseFlag().equals(FlagOption.Y)).collect(Collectors.toList()));
        Optional<CodeGroupDto> taskMetricDto = codeGroupService.findByGroupCode("TASK_METRIC");
        selectTypeModel.setTaskMetric(taskMetricDto.get().getCodeLists().stream().filter(m->m.getUseFlag().equals(FlagOption.Y)).collect(Collectors.toList()));
        Optional<CodeGroupDto> taskTypeDto = codeGroupService.findByGroupCode("TASK_TYPE");
        selectTypeModel.setTaskType(taskTypeDto.get().getCodeLists().stream().filter(m->m.getUseFlag().equals(FlagOption.Y)).collect(Collectors.toList()));

        return selectTypeModel;

    }

    @Override
    public void addDictionary(DictionaryAddModel dictionaryAddModel) {

        Dictionary dictionary = mapper.map(dictionaryAddModel, Dictionary.class);
        if (dictionaryAddModel.isUseFlag()) {
            dictionary.setUseFlag(FlagOption.Y);
        } else {
            dictionary.setUseFlag(FlagOption.N);
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
