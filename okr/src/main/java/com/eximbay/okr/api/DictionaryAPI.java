package com.eximbay.okr.api;

import com.eximbay.okr.constant.DictionaryType;
import com.eximbay.okr.dto.dictionary.DictionaryDto;
import com.eximbay.okr.entity.Dictionary;
import com.eximbay.okr.repository.DictionaryRepository;
import com.eximbay.okr.utils.MapperUtil;

import lombok.AllArgsConstructor;
import ma.glasnost.orika.MapperFacade;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/dictionary")
@AllArgsConstructor
public class DictionaryAPI {
    
    private final DictionaryRepository dictionaryRepository;

    private final MapperFacade mapper;

    @ResponseBody
    @RequestMapping("/datatables")
    public List<DictionaryDto> getDictionaryData() {
        List<Dictionary> dictionaries = dictionaryRepository.findAll();
        List<DictionaryDto> dictionaryList = MapperUtil.mapList(dictionaries, DictionaryDto.class);
        return dictionaryList;
    }

    @ResponseBody
    @RequestMapping("/keyResult/datatables")
    public List<DictionaryDto> getDictionaryOfKeyResult() {
        List<Dictionary> dictionaries = dictionaryRepository.findByDictionaryType(DictionaryType.KEY_RESULT);
        // List<DictionaryDto> dictionaryList = MapperUtil.mapList(dictionaries, DictionaryDto.class);
        // return dictionaryList;
        return mapper.mapAsList(dictionaries, DictionaryDto.class);
    }
}

