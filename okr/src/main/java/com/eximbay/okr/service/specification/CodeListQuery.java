package com.eximbay.okr.service.specification;

import lombok.AllArgsConstructor;
import lombok.Data;

import com.eximbay.okr.constant.FlagOption;
import com.eximbay.okr.entity.CodeGroup_;
import com.eximbay.okr.entity.CodeList;
import com.eximbay.okr.entity.CodeListId_;
import com.eximbay.okr.entity.CodeList_;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class CodeListQuery {

    public Specification<CodeList> findActiveByGroupCodeAndOrderBySortOrderAsc(String groupCode) {
        return (root, query, cb) -> {
            query.orderBy(cb.asc(root.get(CodeList_.sortOrder)));
            return cb.and(
                    cb.equal(root.get(CodeList_.codeListId).get(CodeListId_.groupCode).get(CodeGroup_.groupCode), groupCode),
                    cb.equal(root.get(CodeList_.useFlag), FlagOption.Y)
            );
        };
    }

    public Specification<CodeList> findByGroupCodeAndUseFlagOrderBySortOrderAsc(String groupCode, String useFlag) {
        return (root, query, cb) -> {
            query.orderBy(cb.asc(root.get(CodeList_.sortOrder)));
            return cb.and(
                    cb.equal(root.get(CodeList_.codeListId).get(CodeListId_.groupCode).get(CodeGroup_.groupCode), groupCode),
                    cb.equal(root.get(CodeList_.useFlag), useFlag)
            );
        };
    }


}
