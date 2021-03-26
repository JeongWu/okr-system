package com.eximbay.okr.service.Interface;

import com.eximbay.okr.dto.*;

import java.util.*;

public interface ICompanyService extends ISerivce<CompanyDto, Integer>{
    Optional<CompanyDto> getCurrentCompany();
}
