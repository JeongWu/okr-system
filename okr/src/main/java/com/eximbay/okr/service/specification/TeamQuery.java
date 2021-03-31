package com.eximbay.okr.service.specification;

import com.eximbay.okr.constant.FlagOption;
import com.eximbay.okr.entity.Team;
import com.eximbay.okr.entity.Team_;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@Data
@AllArgsConstructor
public class TeamQuery {

    public Specification<Team> findInUse() {
        return (root, query, cb) -> cb.equal(root.get(Team_.USE_FLAG), FlagOption.Y);
    }

}
