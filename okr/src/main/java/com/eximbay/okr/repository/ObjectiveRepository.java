package com.eximbay.okr.repository;

import com.eximbay.okr.entity.*;
import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface ObjectiveRepository extends JpaRepository<Objective, Integer> {
    List<Objective> findByCloseFlag(String closeFlag);
}
