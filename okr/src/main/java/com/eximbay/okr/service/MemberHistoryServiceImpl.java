package com.eximbay.okr.service;

import com.eximbay.okr.dto.member.MemberDto;
import com.eximbay.okr.dto.memberhistory.MemberHistoryDto;
import com.eximbay.okr.entity.Member;
import com.eximbay.okr.entity.MemberHistory;
import com.eximbay.okr.repository.MemberHistoryRepository;
import com.eximbay.okr.repository.MemberRepository;
import com.eximbay.okr.service.Interface.IMemberHistoryService;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberHistoryServiceImpl implements IMemberHistoryService {

    private final MapperFacade mapper;
    private final MemberHistoryRepository memberHistoryRepository;
    private final MemberRepository memberRepository;

    @Override
    public MemberHistoryDto save(MemberHistoryDto memberDto) {
        MemberHistory memberHistory = mapper.map(memberDto, MemberHistory.class);
        memberHistory = memberHistoryRepository.save(memberHistory);
        return mapper.map(memberHistory, MemberHistoryDto.class);
    }

    @Override
    public List<MemberHistoryDto> findAll() {
        List<MemberHistory> memberHistorys = memberHistoryRepository.findAll();
        return mapper.mapAsList(memberHistorys, MemberHistoryDto.class);
    }

    @Override
    public Optional<MemberHistoryDto> findById(Integer id) {
        Optional<MemberHistory> memberHistory = memberHistoryRepository.findById(id);
        return memberHistory.map(m -> mapper.map(m, MemberHistoryDto.class));
    }

    @Override
    public void remove(MemberHistoryDto memberHistoryDto) {
        MemberHistory memberHistory = mapper.map(memberHistoryDto, MemberHistory.class);
        memberHistoryRepository.delete(memberHistory);
    }

    @Override
    public List<MemberHistoryDto> findByName(String name) {
        List<MemberHistory> memberHistorys = memberHistoryRepository.findByName(name);
        return mapper.mapAsList(memberHistorys, MemberHistoryDto.class);
    }

    @Override
    public List<MemberHistoryDto> findByMember(MemberDto memberDto) {
        Member member = memberRepository.findByMemberSeq(memberDto.getMemberSeq());
        List<MemberHistory> memberHistorys = memberHistoryRepository.findByMember(member);
        return mapper.mapAsList(memberHistorys, MemberHistoryDto.class);
    }

}
