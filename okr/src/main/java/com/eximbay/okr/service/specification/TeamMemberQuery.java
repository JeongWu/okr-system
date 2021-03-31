package com.eximbay.okr.service.specification;

import com.eximbay.okr.constant.AppConst;
import com.eximbay.okr.constant.FlagOption;
import com.eximbay.okr.entity.Member_;
import com.eximbay.okr.entity.TeamMember;
import com.eximbay.okr.entity.id.TeamMemberId_;
import com.eximbay.okr.entity.TeamMember_;
import com.eximbay.okr.entity.Team_;
import com.eximbay.okr.service.Interface.IGenericQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@Data
@AllArgsConstructor
public class TeamMemberQuery implements IGenericQuery<TeamMember> {

    public Specification<TeamMember> findByMemberSeq(Integer memberSeq) {
        return (root, query, cb) -> cb.equal(root.get(TeamMember_.TEAM_MEMBER_ID).get(TeamMemberId_.MEMBER).get(Member_.MEMBER_SEQ), memberSeq);
    }

    public Specification<TeamMember> findActiveTeamOnly() {
        return (root, query, cb) -> cb.equal(root.get(TeamMember_.TEAM_MEMBER_ID).get(TeamMemberId_.TEAM).get(Team_.USE_FLAG), FlagOption.Y);
    }

    public Specification<TeamMember> findActiveMemberOnly() {
        return (root, query, cb) -> cb.equal(root.get(TeamMember_.TEAM_MEMBER_ID).get(TeamMemberId_.MEMBER).get(Team_.USE_FLAG), FlagOption.Y);
    }

    public Specification<TeamMember> findCurrentlyValid() {
        String currentTime = LocalDate.now().format(DateTimeFormatter.ofPattern(AppConst.DATE_FORMAT_YYYYMMDD));
        Specification<TeamMember> result = (root, query, cb) ->
                cb.lessThanOrEqualTo(root.get(TeamMember_.TEAM_MEMBER_ID).get(TeamMemberId_.APPLY_BEGIN_DATE), currentTime);
        result = result.and(
                (root, query, cb) ->
                        cb.greaterThanOrEqualTo(root.get(TeamMember_.APPLY_END_DATE), currentTime)
        );
        return result;
    }

    public Specification<TeamMember> hasLeaderOrManager() {
        Specification<TeamMember> result = (root, query, cb) ->
                cb.equal(root.get(TeamMember_.TEAM_LEAD_FLAG), FlagOption.Y);
        result = result.or(
                (root, query, cb) ->
                        cb.equal(root.get(TeamMember_.TEAM_MANAGER_FLAG), FlagOption.Y)
        );
        return result;
    }

    public Specification<TeamMember> findByTeamSeq(Integer teamSeq) {
        return (root, query, cb) -> cb.equal(root.get(TeamMember_.TEAM_MEMBER_ID).get(TeamMemberId_.TEAM).get(Team_.TEAM_SEQ), teamSeq);
    }

    public Specification<TeamMember> findTeamLeaderOrManager(Integer teamSeq) {
        Specification<TeamMember> result =
                findByTeamSeq(teamSeq)
                        .and(hasLeaderOrManager())
                        .and(findActiveTeamOnly())
                        .and(findActiveMemberOnly())
                        .and(findCurrentlyValid());
        return result;
    }

    public Specification<TeamMember> isCurrentMemberCanEditTeamOkr(Integer teamSeq, Integer memberSeq) {
        Specification<TeamMember> result =
                findByTeamSeq(teamSeq)
                        .and(findByMemberSeq(memberSeq))
                        .and(findActiveTeamOnly())
                        .and(findActiveMemberOnly())
                        .and(findCurrentlyValid())
                        .and(isEqual(TeamMember_.EDIT_TEAM_OKR_FLAG, FlagOption.Y));
        return result;
    }
}