package com.eximbay.okr.repository;

import com.eximbay.okr.entity.Objective;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ObjectiveRepository extends JpaRepository<Objective, Integer>, JpaSpecificationExecutor<Objective> {
    List<Objective> findByCloseFlag(String closeFlag);

    @Query(value = "select concat(year, '-', quarter, 'Q') as quarter_string " +
            "from objective " +
            "group by year, quarter " +
            "order by year desc, quarter desc ", nativeQuery = true)
    List<String> findAllQuarters();

    @Query("SELECT SUM(o.proportion) FROM Objective o WHERE o.closeFlag = 'N' AND o.objectiveType = ?1 AND o.year = ?2 AND o.quarter = ?3")
    int sumProportionsOfActiveObjectivesInQuarter(String objectiveType, int year, int quarter);
}
