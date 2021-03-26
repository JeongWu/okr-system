package com.eximbay.okr.service.specification;

import java.time.Instant;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.eximbay.okr.constant.AppConst;
import com.eximbay.okr.dto.okrScheduleHistory.ScheduleHistoryDatatablesInput;
import com.eximbay.okr.entity.Team;
import com.eximbay.okr.entity.TeamHistory;
import com.eximbay.okr.entity.TeamHistory_;
import com.eximbay.okr.utils.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Service
@Data
@AllArgsConstructor
public class TeamHistoryQuery {

    public Specification<TeamHistory> createdAfterDate(Instant instant){
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get(TeamHistory_.CREATED_DATE), instant);
    }

    public Specification<TeamHistory> createdBeforeDate(Instant instant){
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get(TeamHistory_.CREATED_DATE), instant);
    }
    
    public Specification<TeamHistory> createTeam(Team team){
    	return (root, query, cb) -> cb.equal(root.get(TeamHistory_.TEAM), team);
    }

    public Specification<TeamHistory> buildQueryForDatatables(ScheduleHistoryDatatablesInput input){
        Specification<TeamHistory> query = Specification.where(null);
        
        if (!StringUtils.isNullOrEmpty(input.getBeginDate())){
            query = query.and(createdAfterDate(StringUtils.stringToInstant(input.getBeginDate(), AppConst.DATE_FORMAT_YYYY_MM_DD, false)));
        }
        if (!StringUtils.isNullOrEmpty(input.getEndDate())){
            query = query.and(createdBeforeDate(StringUtils.stringToInstant(input.getEndDate(), AppConst.DATE_FORMAT_YYYY_MM_DD, true)));
        }

        return query;
    }
    
    public Specification<TeamHistory> buildQueryForDatatablesTeam(Team team, ScheduleHistoryDatatablesInput input){
        Specification<TeamHistory> query = Specification.where(null);
        
        query = query.and(createTeam(team));
//        query = query.and(createDivision(division));
        if (!StringUtils.isNullOrEmpty(input.getBeginDate())){
            query = query.and(createdAfterDate(StringUtils.stringToInstant(input.getBeginDate(), AppConst.DATE_FORMAT_YYYY_MM_DD, false)));
        }
        if (!StringUtils.isNullOrEmpty(input.getEndDate())){
            query = query.and(createdBeforeDate(StringUtils.stringToInstant(input.getEndDate(), AppConst.DATE_FORMAT_YYYY_MM_DD, true)));
        }

        return query;
    }
}
