package com.eximbay.okr.entity.id;

import com.eximbay.okr.entity.EvaluationOkr;
import com.eximbay.okr.entity.Objective;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Data
@Embeddable
@EqualsAndHashCode(exclude = { "evaluationOkr", "objective"})
@ToString(exclude = { "evaluationOkr", "objective"} )
public class EvaluationObjectiveId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "OKR_SEQ", nullable = false)
    EvaluationOkr evaluationOkr;

    @ManyToOne
    @JoinColumn(name = "OBJECTIVE_SEQ", nullable = false)
    Objective objective;
}
