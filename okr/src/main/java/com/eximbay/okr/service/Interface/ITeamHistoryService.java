package com.eximbay.okr.service.Interface;


import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eximbay.okr.dto.MemberDto;
import com.eximbay.okr.dto.MemberHistoryDto;
import com.eximbay.okr.dto.TeamDto;
import com.eximbay.okr.dto.TeamHistoryDto;
import com.eximbay.okr.entity.MemberHistory;

public interface ITeamHistoryService extends ISerivce<TeamHistoryDto, Integer> {
}
