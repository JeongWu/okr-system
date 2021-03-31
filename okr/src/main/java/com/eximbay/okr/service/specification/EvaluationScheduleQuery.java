package com.eximbay.okr.service.specification;

import java.time.Instant;

import com.eximbay.okr.constant.AppConst;
import com.eximbay.okr.dto.evaluationschedule.EvaluationScheduleDatatablesInput;
import com.eximbay.okr.entity.EvaluationSchedule;
import com.eximbay.okr.entity.EvaluationSchedule_;
import com.eximbay.okr.utils.StringUtils;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Data;

@Service
@Data
@AllArgsConstructor
public class EvaluationScheduleQuery {

    public Specification<EvaluationSchedule> createdAfterDate(Instant instant){
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get(EvaluationSchedule_.CREATED_DATE), instant);
    }

    public Specification<EvaluationSchedule> createdBeforeDate(Instant instant){
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get(EvaluationSchedule_.CREATED_DATE), instant);
    }

    public Specification<EvaluationSchedule> buildQueryForDatatables(EvaluationScheduleDatatablesInput input){
        Specification<EvaluationSchedule> query = Specification.where(null);
        if (!StringUtils.isNullOrEmpty(input.getBeginDate())){
            query = query.and(createdAfterDate(StringUtils.stringToInstant(input.getBeginDate(), AppConst.DATE_FORMAT_YYYY_MM_DD, false)));
        }
        if (!StringUtils.isNullOrEmpty(input.getEndDate())){
            query = query.and(createdBeforeDate(StringUtils.stringToInstant(input.getEndDate(), AppConst.DATE_FORMAT_YYYY_MM_DD, true)));
        }
        return query;
    }
}
