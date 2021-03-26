package com.eximbay.okr.repository;

import com.eximbay.okr.entity.*;
import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface ObjectiveRepository extends JpaRepository<Objective, Integer>, JpaSpecificationExecutor<Objective> {
    List<Objective> findByCloseFlag(String closeFlag);
    @Query(value = "select concat(year, '-', quarter, 'Q') as quarter_string " +
            "from objective " +
            "group by year, quarter " +
            "order by year desc, quarter desc " , nativeQuery = true)
    List<String> findAllQuarters();
}
