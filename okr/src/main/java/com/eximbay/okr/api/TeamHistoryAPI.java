package com.eximbay.okr.api;

import com.eximbay.okr.dto.team.TeamDto;
import com.eximbay.okr.dto.teammember.TeamMemberDto;
import com.eximbay.okr.dto.okrschedulehistory.ScheduleHistoryDatatablesInput;
import com.eximbay.okr.entity.TeamHistory;
import com.eximbay.okr.service.Interface.ITeamHistoryService;
import com.eximbay.okr.service.Interface.ITeamMemberService;
import com.eximbay.okr.service.Interface.ITeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/team-histories")
public class TeamHistoryAPI {

    private final ITeamHistoryService teamHistoryService;
    private final ITeamService teamService;
    private final ITeamMemberService teamMemberService;
    
    @RequestMapping("/datatables/{id}")
    public DataTablesOutput<TeamHistory> getById(@Valid @RequestBody ScheduleHistoryDatatablesInput input, 
    												@PathVariable Integer id) {
    	TeamDto dto = teamService.findById(id).orElseThrow(()->new NullPointerException("Id doesn't exist"));
    	DataTablesOutput<TeamHistory> data = teamHistoryService.getDataForDatatablesTeam(dto, input);
    	return data;
    }
    
    @RequestMapping("datatables/member/{id}")
    public List<TeamMemberDto> getMemberData1(@PathVariable("id") Integer id){
        TeamDto dto = teamService.findById(id)
                .orElseThrow(()-> new NullPointerException("Id doesn't exist"));
        List<TeamMemberDto> teams = teamMemberService.findSearchBelongTeam(dto);
        return teams;
    }
}
