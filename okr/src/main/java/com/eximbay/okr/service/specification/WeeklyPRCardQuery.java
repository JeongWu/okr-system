package com.eximbay.okr.service.specification;

import com.eximbay.okr.dto.weekly.DateInput;
import com.eximbay.okr.dto.weekly.MemberDatatablesInput;
import com.eximbay.okr.entity.Member_;
import com.eximbay.okr.entity.WeeklyPRCard;
import com.eximbay.okr.entity.WeeklyPRCard_;
import com.eximbay.okr.utils.StringUtils;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Data;

@Service
@Data
@AllArgsConstructor
public class WeeklyPRCardQuery {

    public Specification<WeeklyPRCard> findByYear(Integer year) {
        return (root, query, cb) -> cb.equal(root.get(WeeklyPRCard_.YEAR), year);
    }

    public Specification<WeeklyPRCard> findByWeek(Integer week) {
        return (root, query, cb) -> cb.equal(root.get(WeeklyPRCard_.WEEK), week);
    }

    public Specification<WeeklyPRCard> findByMemberSeq(Integer memberSeq) {
        return (root, query, cb) -> cb.equal(root.get(WeeklyPRCard_.MEMBER).get(Member_.MEMBER_SEQ), memberSeq);
    }


    public Specification<WeeklyPRCard> buildQueryForDatatables(MemberDatatablesInput input) {
        Specification<WeeklyPRCard> query = Specification.where(null);
        if (!StringUtils.isNullOrEmpty(input.getYear())) {
            query = query.and(findByYear(Integer.parseInt(input.getYear())));
        }
        if (!StringUtils.isNullOrEmpty(input.getWeek())) {
            query = query.and(findByWeek(Integer.parseInt(input.getWeek())));
        }
        return query;
    }

    public Specification<WeeklyPRCard> buildQueryMembersDatatables(DateInput input) {
        Specification<WeeklyPRCard> query = Specification.where(null);
        if (!StringUtils.isNullOrEmpty(input.getYear())) {
            query = query.and(findByYear(Integer.parseInt(input.getYear())));
        }
        if (!StringUtils.isNullOrEmpty(input.getWeek())) {
            query = query.and(findByWeek(Integer.parseInt(input.getWeek())));
        }
        return query;
    }
}
