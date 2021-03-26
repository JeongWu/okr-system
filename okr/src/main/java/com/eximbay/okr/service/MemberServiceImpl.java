package com.eximbay.okr.service;

import com.eximbay.okr.constant.FlagOption;
import com.eximbay.okr.constant.MemberPosition;
import com.eximbay.okr.dto.*;
import com.eximbay.okr.entity.*;
import com.eximbay.okr.repository.*;
import com.eximbay.okr.service.Interface.*;
import com.eximbay.okr.service.specification.MemberQuery;
import lombok.*;
import ma.glasnost.orika.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@AllArgsConstructor
@Transactional
public class MemberServiceImpl implements IMemberService {
    private final MemberRepository memberRepository;
    private final MemberQuery memberQuery;
    private final MapperFacade mapper;

    @Override
    public List<MemberDto> findAll() {
        List<Member> members = memberRepository.findAll();
        return mapper.mapAsList(members, MemberDto.class);
    }

    @Override
    public Optional<MemberDto> findById(Integer id) {
        Optional<Member> member = memberRepository.findById(id);
        return member.map(m-> mapper.map(m, MemberDto.class));
    }

    @Override
    public void remove(MemberDto memberDto) {
        Member member = mapper.map(memberDto, Member.class);
        memberRepository.delete(member);
    }

    @Override
    public MemberDto save(MemberDto memberDto) {
        Member member = mapper.map(memberDto, Member.class);
        member = memberRepository.save(member);
        return mapper.map(member, MemberDto.class);
    }

    @Override
    public Optional<MemberDto> findByEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        return member.map(m-> mapper.map(m, MemberDto.class));
    }

    @Override
    public List<MemberDto> findActiveMembers() {
        return mapper.mapAsList(memberRepository.findAll(memberQuery.findActiveMember()), MemberDto.class);
    }

    @Override
    public List<MemberDto> findActiveLeadOrDirectorMembers() {
        List<Member> members = memberRepository.findAll(
                memberQuery.findActiveMember()
                .and(memberQuery.findLeadOrDirectorMember())
        );
        return mapper.mapAsList(members, MemberDto.class);
    }
}
