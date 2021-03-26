package com.eximbay.okr.repository;

import com.eximbay.okr.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
    Optional<Company> findFirstByOrderByCompanySeq();
}
