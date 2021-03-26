package com.eximbay.okr.service;

import com.eximbay.okr.constant.*;
import com.eximbay.okr.dto.*;
import com.eximbay.okr.entity.*;
import com.eximbay.okr.repository.*;
import com.eximbay.okr.service.Interface.*;
import lombok.*;
import ma.glasnost.orika.*;
import org.springframework.core.env.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.*;

@Service
@AllArgsConstructor
@Transactional
public class CompanyServiceImpl implements ICompanyService {
    private final CompanyRepository companyRepository;
    private final Environment environment;
    private final MapperFacade mapper;

    @Override
    public List<CompanyDto> findAll() {
        List<Company> companies = companyRepository.findAll();
        return mapper.mapAsList(companies, CompanyDto.class);
    }

    @Override
    @Transactional
    public Optional<CompanyDto> findById(Integer id) {
        Optional<Company> company = companyRepository.findById(id);
        Optional<CompanyDto> companyDto = company.map(m-> mapper.map(m, CompanyDto.class));
        return companyDto;
    }

    @Override
    public void remove(CompanyDto companyDto) {
        Company company = mapper.map(companyDto, Company.class);
        companyRepository.delete(company);
    }

    @Override
    public CompanyDto save(CompanyDto companyDto) {
        Company company = mapper.map(companyDto, Company.class);
        company = companyRepository.save(company);
        return mapper.map(company, CompanyDto.class);
    }

    @Override
    public Optional<CompanyDto> getCurrentCompany() {
        String companySeq = environment.getProperty("application.company.seq");
        Integer id = Integer.valueOf(Optional.ofNullable(companySeq).orElse(AppConst.DEFAULT_COMPANY_SEQ));
        return findById(id);
    }
}
