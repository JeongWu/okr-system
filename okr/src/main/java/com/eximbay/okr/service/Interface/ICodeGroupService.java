package com.eximbay.okr.service.Interface;

import java.util.Optional;

import com.eximbay.okr.dto.CodeGroupDto;
// import com.eximbay.okr.model.dictionary.CodeGroupModel;

public interface ICodeGroupService extends ISerivce<CodeGroupDto, Integer>{

    Optional<CodeGroupDto> findByGroupCode(String code);
    // Optional<CodeGroupDto> get
    // CodeGroupModel buildCodeGroupModel();
    
}
