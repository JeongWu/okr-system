package com.eximbay.okr.service.specification;

import com.eximbay.okr.constant.AppConst;
import com.eximbay.okr.constant.FlagOption;
import com.eximbay.okr.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@Data
@AllArgsConstructor
public class TeamMemberQuery {

    public Specification<TeamMember> findByTeamSeq(Integer teamSeq){
        return (root, query, cb) -> cb.equal(root.get(TeamMember_.TEAM_MEMBER_ID).get(TeamMemberId_.TEAM).get(Team_.TEAM_SEQ), teamSeq);
    }
   
    public Specification<TeamMember> findByMemberSeq(Integer memberSeq){
        return (root, query, cb) -> cb.equal(root.get(TeamMember_.TEAM_MEMBER_ID).get(TeamMemberId_.MEMBER).get(Member_.MEMBER_SEQ), memberSeq);
    }

    public Specification<TeamMember> findActiveTeamOnly(){
        return (root, query, cb) -> cb.equal(root.get(TeamMember_.TEAM_MEMBER_ID).get(TeamMemberId_.TEAM).get(Team_.USE_FLAG), FlagOption.Y);
    }

    public Specification<TeamMember> findActiveMemberOnly(){
        return (root, query, cb) -> cb.equal(root.get(TeamMember_.TEAM_MEMBER_ID).get(TeamMemberId_.MEMBER).get(Team_.USE_FLAG), FlagOption.Y);
    }

    public Specification<TeamMember> findCurrentlyValid(){
        String currentTime = LocalDate.now().format(DateTimeFormatter.ofPattern(AppConst.DATE_FORMAT_YYYYMMDD));
        Specification<TeamMember> result = (root, query, cb) ->
                cb.lessThanOrEqualTo(root.get(TeamMember_.TEAM_MEMBER_ID).get(TeamMemberId_.APPLY_BEGIN_DATE), currentTime);
        result = result.and(
                (root, query, cb) ->
                        cb.greaterThanOrEqualTo(root.get(TeamMember_.APPLY_END_DATE), currentTime)
        );
        return result;
    }

    public Specification<TeamMember> hasLeaderOrManager(){
        Specification<TeamMember> result = (root, query, cb) ->
                cb.equal(root.get(TeamMember_.TEAM_LEAD_FLAG), FlagOption.Y);
        result = result.or(
                (root, query, cb) ->
                        cb.equal(root.get(TeamMember_.TEAM_MANAGER_FLAG), FlagOption.Y)
        );
        return result;
    }
}
