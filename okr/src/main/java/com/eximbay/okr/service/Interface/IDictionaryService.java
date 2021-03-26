package com.eximbay.okr.service.Interface;

import com.eximbay.okr.dto.dictionary.DictionaryDto;
import com.eximbay.okr.model.dictionary.DictionaryAddModel;
import com.eximbay.okr.model.dictionary.DictionaryUpdateModel;
import com.eximbay.okr.model.dictionary.SelectTypeModel;

public interface IDictionaryService extends ISerivce<DictionaryDto, Integer> {

    SelectTypeModel buildSelectTypeModel();

    void addDictionary(DictionaryAddModel dictionaryAddModel);

    DictionaryUpdateModel buildUpdateDictionaryModel(Integer id);

    void updateFormModel(DictionaryUpdateModel updateFormModel);

}
