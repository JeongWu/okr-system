package com.eximbay.okr.service.specification;

import com.eximbay.okr.constant.AppConst;
import com.eximbay.okr.constant.FlagOption;
import com.eximbay.okr.entity.DivisionMember;
import com.eximbay.okr.entity.DivisionMemberId_;
import com.eximbay.okr.entity.DivisionMember_;
import com.eximbay.okr.entity.Division_;
import com.eximbay.okr.entity.Member_;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@Transactional
@Data
@AllArgsConstructor
public class DivisionMemberQuery {

    public Specification<DivisionMember> findByDivisionSeq(Integer divisionSeq) {
        return (root, query, cb) -> cb.equal(root.get(DivisionMember_.DIVISION_MEMBER_ID).get(DivisionMemberId_.DIVISION).get(Division_.DIVISION_SEQ), divisionSeq);
    }

    public Specification<DivisionMember> findCurrentlyValid() {
        String currentTime = LocalDate.now().format(DateTimeFormatter.ofPattern(AppConst.DATE_FORMAT_YYYYMMDD));
        Specification<DivisionMember> result = (root, query, cb) ->
                cb.lessThanOrEqualTo(root.get(DivisionMember_.DIVISION_MEMBER_ID).get(DivisionMemberId_.APPLY_BEGIN_DATE), currentTime);
        result = result.and(
                (root, query, cb) ->
                        cb.greaterThanOrEqualTo(root.get(DivisionMember_.APPLY_END_DATE), currentTime)
        );
        return result;
    }

    public Specification<DivisionMember> findActiveMemberOnly() {
        return (root, query, cb) -> cb.equal(root.get(DivisionMember_.DIVISION_MEMBER_ID).get(DivisionMemberId_.MEMBER).get(Member_.USE_FLAG), FlagOption.Y);
    }

    public Specification<DivisionMember> isActiveInFuture() {
        String currentTime = LocalDate.now().format(DateTimeFormatter.ofPattern(AppConst.DATE_FORMAT_YYYYMMDD));
        return (root, query, cb) -> cb.greaterThan(root.get(DivisionMember_.DIVISION_MEMBER_ID).get(DivisionMemberId_.APPLY_BEGIN_DATE), currentTime);
    }

    public Specification<DivisionMember> findByMemberSeq(Integer memberSeq) {
        return (root, query, cb) -> cb.equal(root.get(DivisionMember_.DIVISION_MEMBER_ID).get(DivisionMemberId_.MEMBER).get(Member_.MEMBER_SEQ), memberSeq);
    }
}
