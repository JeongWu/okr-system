package com.eximbay.okr.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eximbay.okr.dto.TeamDto;
import com.eximbay.okr.dto.TeamMemberDto;
import com.eximbay.okr.dto.okrScheduleHistory.ScheduleHistoryDatatablesInput;
import com.eximbay.okr.entity.TeamHistory;
import com.eximbay.okr.service.MemberServiceImpl;
import com.eximbay.okr.service.TeamHistoryDataServiceImpl;
import com.eximbay.okr.service.TeamMemberServiceImpl;
import com.eximbay.okr.service.Interface.ITeamService;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@RestController
@RequestMapping("api/team-histories")
public class TeamHistoryAPI {
    private final TeamHistoryDataServiceImpl teamHistoryService;
    private final ITeamService teamService;
    private final MemberServiceImpl memberService;
    private final TeamMemberServiceImpl teamMemberService;
    
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
