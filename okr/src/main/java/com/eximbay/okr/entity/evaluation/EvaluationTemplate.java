package com.eximbay.okr.entity.evaluation;

import com.eximbay.okr.listener.AbstractAuditable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;


@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "EVALUATION_TEMPLATE")
public class EvaluationTemplate extends AbstractAuditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column( name = "TEMPLATE_SEQ")
    private Integer templateSeq;

    @Column(name = "EVALUATION_TYPE", length = 20, nullable = false)
    private String evaluationType;

    @Column(name = "TEMPLATE_NAME", length = 100, nullable = false)
    private String templateName;

    @Column(name = "USE_FLAG", length = 1, nullable = false)
    private String useFlag;

    @OneToMany(mappedBy = "evaluationTemplate")
    @JsonIgnore
    private List<EvaluationTemplateDetail> evaluationTemplateDetails;

}
