package com.eximbay.okr.service.specification;

import com.eximbay.okr.constant.AppConst;
import com.eximbay.okr.dto.okrschedulehistory.ScheduleHistoryDatatablesInput;
import com.eximbay.okr.entity.OkrScheduleHistory;
import com.eximbay.okr.entity.OkrScheduleHistory_;
import com.eximbay.okr.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Data
@AllArgsConstructor
public class OkrScheduleHistoryQuery {

    public Specification<OkrScheduleHistory> createdAfterDate(Instant instant) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get(OkrScheduleHistory_.CREATED_DATE), instant);
    }

    public Specification<OkrScheduleHistory> createdBeforeDate(Instant instant) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get(OkrScheduleHistory_.CREATED_DATE), instant);
    }

    public Specification<OkrScheduleHistory> buildQueryForDatatables(ScheduleHistoryDatatablesInput input) {
        Specification<OkrScheduleHistory> query = Specification.where(null);
        if (!StringUtils.isNullOrEmpty(input.getBeginDate())) {
            query = query.and(createdAfterDate(StringUtils.stringToInstant(input.getBeginDate(), AppConst.DATE_FORMAT_YYYY_MM_DD, false)));
        }
        if (!StringUtils.isNullOrEmpty(input.getEndDate())) {
            query = query.and(createdBeforeDate(StringUtils.stringToInstant(input.getEndDate(), AppConst.DATE_FORMAT_YYYY_MM_DD, true)));
        }
        return query;
    }
}
