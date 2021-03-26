package com.eximbay.okr.repository;

import java.util.List;

import com.eximbay.okr.entity.Dictionary;

import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface DictionaryRepository extends JpaRepository<Dictionary, Integer> {

    List<Dictionary> findByDictionaryType(String dictionaryType);
    
}
