package com.eximbay.okr.service;

import com.eximbay.okr.dto.codegroup.CodeGroupDto;
import com.eximbay.okr.entity.CodeGroup;
import com.eximbay.okr.repository.CodeGroupRepository;
import com.eximbay.okr.service.Interface.ICodeGroupService;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CodeGroupService implements ICodeGroupService {

    private final MapperFacade mapper;
    private final CodeGroupRepository codeGroupRepository;

    @Override
    public List<CodeGroupDto> findAll() {
        List<CodeGroup> codeGroups = codeGroupRepository.findAll();
        return mapper.mapAsList(codeGroups, CodeGroupDto.class);
    }

    @Override
    public Optional<CodeGroupDto> findById(Integer id) {
        Optional<CodeGroup> codeGroup = codeGroupRepository.findById(id);
        Optional<CodeGroupDto> codeGroupDto = codeGroup.map(m -> mapper.map(m, CodeGroupDto.class));
        return codeGroupDto;
    }

    @Override
    public void remove(CodeGroupDto codeGroupDto) {
        CodeGroup codeGroup = mapper.map(codeGroupDto, CodeGroup.class);
        codeGroupRepository.delete(codeGroup);

    }

    @Override
    public CodeGroupDto save(CodeGroupDto codeGroupDto) {
        CodeGroup codeGroup = mapper.map(codeGroupDto, CodeGroup.class);
        codeGroup = codeGroupRepository.save(codeGroup);
        return mapper.map(codeGroup, CodeGroupDto.class);
    }

    @Override
    public Optional<CodeGroupDto> findByGroupCode(String code) {
        Optional<CodeGroup> codeGroup = codeGroupRepository.findByGroupCode(code);
        return codeGroup.map(m -> mapper.map(m, CodeGroupDto.class));
    }

}
