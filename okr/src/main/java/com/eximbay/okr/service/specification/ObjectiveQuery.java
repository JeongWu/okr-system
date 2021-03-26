package com.eximbay.okr.service.specification;

import com.eximbay.okr.constant.FlagOption;
import com.eximbay.okr.entity.Member_;
import com.eximbay.okr.entity.Objective;
import com.eximbay.okr.entity.Objective_;
import com.eximbay.okr.entity.Team_;
import com.eximbay.okr.service.Interface.IGenericQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Data
@AllArgsConstructor
public class ObjectiveQuery implements IGenericQuery<Objective> {

    public Specification<Objective> findActiveObjective(){
        return (root, query, cb) -> cb.equal(root.get(Objective_.CLOSE_FLAG), FlagOption.N);
    }

    public Specification<Objective> findInYear(Integer year){
        return (root, query, cb) -> cb.equal(root.get(Objective_.YEAR), year);
    }

    public Specification<Objective> findInQuarter(Integer quarter){
        return (root, query, cb) -> cb.equal(root.get(Objective_.QUARTER), quarter);
    }

    public Specification<Objective> findByCompany(){
        return (root, query, cb) -> cb.isNotNull(root.get(Objective_.COMPANY));
    }

    public Specification<Objective> findByObjectiveSeqIn(Set<Integer> in){
        return (root, query, cb) -> root.get(Objective_.OBJECTIVE_SEQ).in(in);
    }

    public Specification<Objective> findByTeamSeq(Integer teamSeq){
        return (root, query, cb) -> cb.equal(root.get(Objective_.TEAM).get(Team_.TEAM_SEQ), teamSeq);
    }

    public Specification<Objective> findByMemberSeq(Integer memberSeq){
        return (root, query, cb) -> cb.equal(root.get(Objective_.MEMBER).get(Member_.MEMBER_SEQ), memberSeq);
    }
}
