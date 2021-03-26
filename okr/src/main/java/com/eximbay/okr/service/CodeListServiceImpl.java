package com.eximbay.okr.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.eximbay.okr.dto.CodeListDto;
import com.eximbay.okr.entity.CodeList;
import com.eximbay.okr.repository.CodeListRepository;
import com.eximbay.okr.service.Interface.ICodeListService;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import ma.glasnost.orika.MapperFacade;

@Service
@AllArgsConstructor
@Transactional
public class CodeListServiceImpl implements ICodeListService {

    private final CodeListRepository codeListRepository;
    private final MapperFacade mapper;

    @Override
    public List<CodeListDto> findAll() {
        List<CodeList> codeListIds = codeListRepository.findAll();
        return mapper.mapAsList(codeListIds, CodeListDto.class);
    }


    @Override
    public void remove(CodeListDto codeListDto) {
        CodeList codeList = mapper.map(codeListDto, CodeList.class);
        codeListRepository.delete(codeList);
    }

    @Override
    public CodeListDto save(CodeListDto codeListDto) {
        CodeList codeList = mapper.map(codeListDto, CodeList.class);
        codeList = codeListRepository.save(codeList);
        return mapper.map(codeList, CodeListDto.class);
    }

    @Override
    public Optional<CodeListDto> findById(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
