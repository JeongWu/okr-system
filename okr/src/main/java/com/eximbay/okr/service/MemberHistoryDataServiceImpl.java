package com.eximbay.okr.service;

import java.util.List;
import java.util.Optional;

import com.eximbay.okr.dto.MemberDto;
import com.eximbay.okr.dto.MemberHistoryDto;
import com.google.common.collect.Lists;
import com.eximbay.okr.dto.okrScheduleHistory.ScheduleHistoryDatatablesInput;
import com.eximbay.okr.entity.Member;
import com.eximbay.okr.entity.MemberHistory;
import com.eximbay.okr.repository.MemberHistoryDataRepository;
import com.eximbay.okr.repository.MemberHistoryRepository;
import com.eximbay.okr.service.Interface.IMemberHistoryDataService;
import com.eximbay.okr.service.specification.MemberHistoryQuery;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Data;
import ma.glasnost.orika.MapperFacade;

@Data
@AllArgsConstructor
@Service
public class MemberHistoryDataServiceImpl implements IMemberHistoryDataService {

    private final MemberHistoryDataRepository memberHistoryRepository;
    private final MemberHistoryQuery memberHistoryQuery;
    private final MapperFacade mapper; 


    @Override
    public List<MemberHistoryDto> findAll() {
       List<MemberHistory> historys = Lists.newArrayList(memberHistoryRepository.findAll());
         return mapper.mapAsList(historys, MemberHistoryDto.class);
    }

    @Override
    public Optional<MemberHistoryDto> findById(Integer id) {
        Optional<MemberHistory> historys = memberHistoryRepository.findById(id);
        return historys.map(m-> mapper.map(m, MemberHistoryDto.class));
    }

    @Override
    public void remove(MemberHistoryDto memberHistoryDto) {
        MemberHistory historys = mapper.map(memberHistoryDto, MemberHistory.class);
        memberHistoryRepository.delete(historys);

    }

    @Override
    public MemberHistoryDto save(MemberHistoryDto memberHistoryDto) {
        MemberHistory historys = mapper.map(memberHistoryDto, MemberHistory.class);
        historys = memberHistoryRepository.save(historys);
        return mapper.map(historys, MemberHistoryDto.class);
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
