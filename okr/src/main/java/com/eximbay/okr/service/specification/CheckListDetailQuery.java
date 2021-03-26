package com.eximbay.okr.service.specification;

import com.eximbay.okr.entity.KeyResult;
import com.eximbay.okr.entity.OkrCheckListDetail;
import com.eximbay.okr.entity.OkrChecklistGroup;
import com.eximbay.okr.entity.OkrCheckListDetail_;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Data;

import org.hibernate.criterion.Distinct;
import org.springframework.data.jpa.domain.Specification;

@Service
@Data
@AllArgsConstructor
public class CheckListDetailQuery {

    public Specification<OkrCheckListDetail> createOkrChecklistGroup(OkrChecklistGroup okrChecklistGroup){
        return (root, query, cb) -> cb.equal(root.get(OkrCheckListDetail_.CHECK_LIST),okrChecklistGroup);
    }

    public Specification<OkrCheckListDetail> buildQueryForChecklistGroup(OkrChecklistGroup okrChecklistGroup){

        Specification<OkrCheckListDetail> query = Specification.where(null);
        query = query.and(createOkrChecklistGroup(okrChecklistGroup));

        return query;
    }
    
}
