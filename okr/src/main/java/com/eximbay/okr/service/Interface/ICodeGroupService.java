package com.eximbay.okr.service.Interface;

import java.util.Optional;

import com.eximbay.okr.dto.CodeGroupDto;

public interface ICodeGroupService extends ISerivce<CodeGroupDto, Integer>{

    Optional<CodeGroupDto> findByGroupCode(String code);
    
}
