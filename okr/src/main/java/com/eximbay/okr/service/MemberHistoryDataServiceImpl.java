package com.eximbay.okr.service;

import com.eximbay.okr.dto.member.MemberDto;
import com.eximbay.okr.dto.memberhistory.MemberHistoryDto;
import com.eximbay.okr.dto.okrschedulehistory.ScheduleHistoryDatatablesInput;
import com.eximbay.okr.entity.Member;
import com.eximbay.okr.entity.MemberHistory;
import com.eximbay.okr.repository.MemberHistoryDataRepository;
import com.eximbay.okr.service.Interface.IMemberHistoryDataService;
import com.eximbay.okr.service.specification.MemberHistoryQuery;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Data
@RequiredArgsConstructor
@Service
public class MemberHistoryDataServiceImpl implements IMemberHistoryDataService {

    private final MapperFacade mapper;
    private final MemberHistoryDataRepository memberHistoryRepository;
    private final MemberHistoryQuery memberHistoryQuery;


    @Override
    public List<MemberHistoryDto> findAll() {
        List<MemberHistory> histories = Lists.newArrayList(memberHistoryRepository.findAll());
        return mapper.mapAsList(histories, MemberHistoryDto.class);
    }

    @Override
    public Optional<MemberHistoryDto> findById(Integer id) {
        Optional<MemberHistory> history = memberHistoryRepository.findById(id);
        return history.map(m -> mapper.map(m, MemberHistoryDto.class));
    }

    @Override
    public void remove(MemberHistoryDto memberHistoryDto) {
        MemberHistory histories = mapper.map(memberHistoryDto, MemberHistory.class);
        memberHistoryRepository.delete(histories);

    }

    @Override
    public MemberHistoryDto save(MemberHistoryDto memberHistoryDto) {
        MemberHistory histories = mapper.map(memberHistoryDto, MemberHistory.class);
        histories = memberHistoryRepository.save(histories);
        return mapper.map(histories, MemberHistoryDto.class);
    }

    @Override
    public DataTablesOutput<MemberHistory> getDataForDatatablesMember(MemberDto memberDto,
                                                                      ScheduleHistoryDatatablesInput input) {
        Member member = mapper.map(memberDto, Member.class);
        DataTablesOutput<MemberHistory> output = memberHistoryRepository.findAll(input,
                memberHistoryQuery.buildQueryForDatatablesMember(member, input));
        return output;
    }

}
