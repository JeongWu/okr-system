package com.eximbay.okr.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.eximbay.okr.constant.FlagOption;
import com.eximbay.okr.listener.AbstractAuditable;

import groovy.transform.EqualsAndHashCode;
import lombok.Data;

@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "dictionary")
@Entity
public class Dictionary extends AbstractAuditable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "DICTIONARY_SEQ", length = 11)
    private Integer dictionarySeq;

    @Column(name = "DICTIONARY_TYPE", length = 20, nullable = false)
    private String dictionaryType;

    @Column(name = "JOB_TYPE", length = 20, nullable = false)
    private String jobType;

    @Column(name = "SENTENCE", length = 255, nullable = false)
    private String sentence;

    @Column(name = "CATEGORY_GROUP", length = 20)
    private String categoryGroup;

    @Column(name = "CATEGORY", length = 20)
    private String category;

    @Column(name = "TASK_TYPE", length = 20)
    private String taskType;

    @Column(name = "TASK_METRIC", length = 20)
    private String taskMetric;

    @Column(name = "TASK_INDICATOR", length = 20)
    private String taskIndicator;

    @Column(name = "DESCRIPTION", length = 1000)
    private String description;

    @Column(name = "USE_FLAG", length = 1, nullable = false)
    private String useFlag = FlagOption.Y;


}
