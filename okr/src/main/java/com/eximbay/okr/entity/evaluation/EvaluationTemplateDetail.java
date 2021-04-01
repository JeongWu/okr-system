package com.eximbay.okr.entity.evaluation;

import com.eximbay.okr.listener.AbstractAuditable;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "EVALUATION_TEMPLATE_DETAIL")
public class EvaluationTemplateDetail extends AbstractAuditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DETAIL_SEQ")
    private Integer detailSeq;

    @Column( name = "TEMPLATE_SEQ", insertable = false, updatable = false)
    private Integer templateSeq;

    @ManyToOne
    @JoinColumn(name = "TEMPLATE_SEQ")
    private EvaluationTemplate evaluationTemplate;

    @Column(name = "QUESTION", nullable = false)
    private String question;

    @Column(name = "ANSWER_GROUP_CODE", length = 30, nullable = false)
    private String answerGroupCode;

    @Column(name = "USE_FLAG", length = 1, nullable = false)
    private String useFlag;

}
