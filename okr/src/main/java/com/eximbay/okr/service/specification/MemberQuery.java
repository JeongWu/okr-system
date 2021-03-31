package com.eximbay.okr.service.specification;

import com.eximbay.okr.constant.FlagOption;
import com.eximbay.okr.constant.MemberPosition;
import com.eximbay.okr.entity.Member;
import com.eximbay.okr.entity.Member_;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@Data
@AllArgsConstructor
public class MemberQuery {

    public Specification<Member> findActiveMember() {
        return (root, query, cb) -> cb.equal(root.get(Member_.USE_FLAG), FlagOption.Y);
    }

    public Specification<Member> findLeadOrDirectorMember() {
        Specification<Member> result = (root, query, cb) -> cb.equal(root.get(Member_.POSITION), MemberPosition.LEAD);
        result = result.or((root, query, cb) -> cb.equal(root.get(Member_.POSITION), MemberPosition.DIRECTOR));
        return result;
    }
}
