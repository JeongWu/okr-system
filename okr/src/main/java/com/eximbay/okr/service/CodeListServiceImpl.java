package com.eximbay.okr.service;

import com.eximbay.okr.dto.codelist.CodeListDto;
import com.eximbay.okr.entity.CodeList;
import com.eximbay.okr.repository.CodeListRepository;
import com.eximbay.okr.service.Interface.ICodeListService;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CodeListServiceImpl implements ICodeListService {

    private final MapperFacade mapper;
    private final CodeListRepository codeListRepository;

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
        return Optional.empty();
    }

}
