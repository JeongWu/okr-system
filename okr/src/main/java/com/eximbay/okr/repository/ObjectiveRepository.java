package com.eximbay.okr.repository;

import com.eximbay.okr.entity.Member;
import com.eximbay.okr.entity.Objective;
import com.eximbay.okr.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ObjectiveRepository extends JpaRepository<Objective, Integer>, JpaSpecificationExecutor<Objective> {

    String SUM_ACTIVE_PROPORTION_OF_COMPANY_BY_YEAR_AND_QUARTER =
            "SELECT COALESCE(SUM(o.proportion), 0) FROM Objective o WHERE o.closeFlag = 'N' AND o.objectiveType = '" + Objective.OBJECTIVE_TYPE_COMPANY + "' AND o.year = ?1 AND o.quarter = ?2";
    String SUM_ACTIVE_PROPORTION_OF_TEAM_BY_YEAR_AND_QUARTER =
            "SELECT COALESCE(SUM(o.proportion), 0) FROM Objective o WHERE o.closeFlag = 'N' AND o.objectiveType = '" + Objective.OBJECTIVE_TYPE_TEAM + "' AND o.teamSeq = ?1 AND o.year = ?2 AND o.quarter = ?3";
    String SUM_ACTIVE_PROPORTION_OF_MEMBER_BY_YEAR_AND_QUARTER =
            "SELECT COALESCE(SUM(o.proportion), 0) FROM Objective o WHERE o.closeFlag = 'N' AND o.objectiveType = '" + Objective.OBJECTIVE_TYPE_MEMBER + "' AND o.memberSeq = ?1 AND o.year = ?2 AND o.quarter = ?3";

    List<Objective> findByCloseFlag(String closeFlag);

    @Query(value = "select concat(year, '-', quarter, 'Q') as quarter_string " +
            "from objective " +
            "group by year, quarter " +
            "order by year desc, quarter desc ", nativeQuery = true)
    List<String> findAllQuarters();

    @Query(value = SUM_ACTIVE_PROPORTION_OF_COMPANY_BY_YEAR_AND_QUARTER)
    int sumActiveProportionOfCompanyByYearAndQuarter(int year, int quarter);

    @Query(value = SUM_ACTIVE_PROPORTION_OF_TEAM_BY_YEAR_AND_QUARTER)
    int sumActiveProportionOfTeamByYearAndQuarter(int teamSeq, int year, int quarter);

    Optional<Objective> findByTeamAndObjectiveSeq(Team team, int objectiveSeq);

    @Query(value = SUM_ACTIVE_PROPORTION_OF_MEMBER_BY_YEAR_AND_QUARTER)
    int sumActiveProportionOfMemberByYearAndQuarter(int memberSeq, int year, int quarter);

    Optional<Objective> findByMemberAndObjectiveSeq(Member member, int objectiveSeq);
}
