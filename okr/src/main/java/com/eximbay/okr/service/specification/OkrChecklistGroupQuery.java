package com.eximbay.okr.service.specification;

import com.eximbay.okr.constant.AppConst;
import com.eximbay.okr.dto.okrchecklistgroup.ChecklistGroupDatatablesInput;
import com.eximbay.okr.entity.Objective;
import com.eximbay.okr.entity.OkrChecklistGroup;
import com.eximbay.okr.entity.OkrChecklistGroup_;
import com.eximbay.okr.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Data
@AllArgsConstructor
public class OkrChecklistGroupQuery {

    public Specification<OkrChecklistGroup> createdAfterDate(Instant instant) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get(OkrChecklistGroup_.CREATED_DATE), instant);
    }

    public Specification<OkrChecklistGroup> createdBeforeDate(Instant instant) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get(OkrChecklistGroup_.CREATED_DATE), instant);
    }

    public Specification<OkrChecklistGroup> createObjective(Objective objective) {
        return (root, query, cb) -> cb.equal(root.get(OkrChecklistGroup_.OBJECTIVE), objective);
    }


    public Specification<OkrChecklistGroup> buildQueryForDatatables(ChecklistGroupDatatablesInput input) {

        Specification<OkrChecklistGroup> query = Specification.where(null);

        if (!StringUtils.isNullOrEmpty(input.getBeginDate())) {
            query = query.and(createdAfterDate(StringUtils.stringToInstant(input.getBeginDate(), AppConst.DATE_FORMAT_YYYY_MM_DD, false)));
        }
        if (!StringUtils.isNullOrEmpty(input.getEndDate())) {
            query = query.and(createdBeforeDate(StringUtils.stringToInstant(input.getEndDate(), AppConst.DATE_FORMAT_YYYY_MM_DD, true)));
        }
        return query;
    }

    public Specification<OkrChecklistGroup> buildQueryForDatatablesObjective(Objective objective, ChecklistGroupDatatablesInput input) {

        Specification<OkrChecklistGroup> query = Specification.where(null);

        query = query.and(createObjective(objective));

        if (!StringUtils.isNullOrEmpty(input.getBeginDate())) {
            query = query.and(createdAfterDate(StringUtils.stringToInstant(input.getBeginDate(), AppConst.DATE_FORMAT_YYYY_MM_DD, false)));
        }
        if (!StringUtils.isNullOrEmpty(input.getEndDate())) {
            query = query.and(createdBeforeDate(StringUtils.stringToInstant(input.getEndDate(), AppConst.DATE_FORMAT_YYYY_MM_DD, true)));
        }
        return query;
    }
}
