package com.eximbay.okr.entity;

import com.eximbay.okr.constant.FlagOption;
import com.eximbay.okr.listener.AbstractAuditable;
import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "objective_relation")
@Entity
public class ObjectiveRelation extends AbstractAuditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "RELATION_SEQ", length = 11)
    private Integer relationSeq;

    @ManyToOne
    @JoinColumn(name = "OBJECTIVE_SEQ", nullable = false)
    private Objective objective;

    @ManyToOne
    @JoinColumn(name = "TOP_KEY_RESULT_SEQ")
    private KeyResult topKeyResult;

    @ManyToOne
    @JoinColumn(name = "RELATED_OBJECTIVE_SEQ")
    private Objective relatedObjective;

    @Column(name = "USE_FLAG", length = 1, nullable = false)
    private String useFlag = FlagOption.Y;
}
