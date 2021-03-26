package com.eximbay.okr.service.specification;

import java.time.Instant;

import com.eximbay.okr.constant.AppConst;
import com.eximbay.okr.dto.MemberDto;
import com.eximbay.okr.dto.okrScheduleHistory.ScheduleHistoryDatatablesInput;
import com.eximbay.okr.entity.Member;
import com.eximbay.okr.entity.MemberHistory;
import com.eximbay.okr.entity.MemberHistory_;
import com.eximbay.okr.utils.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Data;

@Service
@Data
@AllArgsConstructor
public class MemberHistoryQuery {

    public Specification<MemberHistory> createdAfterDate(Instant instant){
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get(MemberHistory_.CREATED_DATE), instant);
    }

    public Specification<MemberHistory> createdBeforeDate(Instant instant){
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get(MemberHistory_.CREATED_DATE), instant);
    }

    public Specification<MemberHistory> createMember(Member member){
        return (root, query, cb) -> cb.equal(root.get(MemberHistory_.MEMBER), member);
    }

    public Specification<MemberHistory> buildQueryForDatatables(ScheduleHistoryDatatablesInput input){
        Specification<MemberHistory> query = Specification.where(null);

        if (!StringUtils.isNullOrEmpty(input.getBeginDate())){
            query = query.and(createdAfterDate(StringUtils.stringToInstant(input.getBeginDate(), AppConst.DATE_FORMAT_YYYY_MM_DD, false)));
        }
        if (!StringUtils.isNullOrEmpty(input.getEndDate())){
            query = query.and(createdBeforeDate(StringUtils.stringToInstant(input.getEndDate(), AppConst.DATE_FORMAT_YYYY_MM_DD, true)));
        }
        return query;
    }

    public Specification<MemberHistory> buildQueryForDatatablesMember(Member member, ScheduleHistoryDatatablesInput input){
        Specification<MemberHistory> query = Specification.where(null);

        query = query.and(createMember(member));

        if (!StringUtils.isNullOrEmpty(input.getBeginDate())){
            query = query.and(createdAfterDate(StringUtils.stringToInstant(input.getBeginDate(), AppConst.DATE_FORMAT_YYYY_MM_DD, false)));
        }
        if (!StringUtils.isNullOrEmpty(input.getEndDate())){
            query = query.and(createdBeforeDate(StringUtils.stringToInstant(input.getEndDate(), AppConst.DATE_FORMAT_YYYY_MM_DD, true)));
        }
        return query;
    }

    

}
