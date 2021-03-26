package com.eximbay.okr.service;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import com.eximbay.okr.dto.MemberDto;
import com.eximbay.okr.dto.MemberHistoryDto;
import com.eximbay.okr.entity.Member;
import com.eximbay.okr.entity.MemberHistory;
import com.eximbay.okr.repository.MemberHistoryRepository;
import com.eximbay.okr.repository.MemberRepository;
import com.eximbay.okr.service.Interface.IMemberHistoryService;
import ma.glasnost.orika.MapperFacade;

@Service
@AllArgsConstructor
@Transactional
public class MemberHistoryServiceImpl implements IMemberHistoryService {
	
	@Autowired
	private MemberHistoryRepository memberHistoryRepository;
	
    @Autowired
    private MemberRepository memberRepository;
	
    @Autowired
    MapperFacade mapper;

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
	
}
