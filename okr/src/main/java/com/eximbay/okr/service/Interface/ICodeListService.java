package com.eximbay.okr.service.Interface;

import com.eximbay.okr.dto.codelist.CodeListDto;

import java.util.List;

public interface ICodeListService extends IService<CodeListDto, Integer> {

    List<CodeListDto> findByGroupCodeAndUseFlagOrderBySortOrderAsc(String groupCode, String useFlag);
}
