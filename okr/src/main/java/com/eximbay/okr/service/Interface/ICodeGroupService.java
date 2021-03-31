package com.eximbay.okr.service.Interface;

import com.eximbay.okr.dto.codegroup.CodeGroupDto;

import java.util.Optional;

public interface ICodeGroupService extends IService<CodeGroupDto, Integer> {

    Optional<CodeGroupDto> findByGroupCode(String code);

}
