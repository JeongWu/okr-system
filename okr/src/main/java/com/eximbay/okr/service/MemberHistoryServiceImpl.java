package com.eximbay.okr.service;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.stereotype.Service;
import com.eximbay.okr.dto.MemberDto;
import com.eximbay.okr.dto.MemberHistoryDto;
import com.eximbay.okr.entity.Member;
import com.eximbay.okr.entity.MemberHistory;
import com.eximbay.okr.repository.MemberHistoryRepository;
import com.eximbay.okr.repository.MemberRepository;
import com.eximbay.okr.service.Interface.IMemberHistoryService;
import com.eximbay.okr.utils.PagingUtils;

import org.springframework.data.domain.Pageable;


import ma.glasnost.orika.MapperFacade;

@Service
public class MemberHistoryServiceImpl implements IMemberHistoryService {
	
	@Autowired
	private MemberHistoryRepository memberHistoryRepository;
	
    @Autowired
    private MemberRepository memberRepository;
	
    @Autowired
    MapperFacade mapper;
    
    private EntityManager em;
	
    // 다시
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
        return memberHistory.map(m-> mapper.map(m, MemberHistoryDto.class));
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
	
	
	@Override
	public List<MemberHistoryDto> findSearch(MemberDto memberDto, LocalDate from, LocalDate to, String justification) {	
		Member member = memberRepository.findByMemberSeq(memberDto.getMemberSeq());
		List<MemberHistory> memberHistorys = memberHistoryRepository
				.findByMemberAndUpdatedDateBetweenAndJustificationContaining
				(member, from.atStartOfDay().toInstant(ZoneOffset.UTC), to.atStartOfDay().toInstant(ZoneOffset.UTC), justification);
		return mapper.mapAsList(memberHistorys, MemberHistoryDto.class);
	}

	@Override
	public Page<MemberHistory> findSearchPage(MemberDto memberDto, LocalDate from, LocalDate to, String justification,
			 Pageable pageable) {
			Member member = memberRepository.findByMemberSeq(memberDto.getMemberSeq());
			Page<MemberHistory> memberHistorys = memberHistoryRepository
			.findByMemberAndUpdatedDateBetweenAndJustificationContaining
			(member, from.atStartOfDay().toInstant(ZoneOffset.UTC), to.atStartOfDay().toInstant(ZoneOffset.UTC), justification, PagingUtils.buildPageRequest(pageable));
			return memberHistorys;
	}
	
	

}
