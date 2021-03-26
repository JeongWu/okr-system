package com.eximbay.okr.service;

import com.eximbay.okr.constant.AppConst;
import com.eximbay.okr.dto.*;
import com.eximbay.okr.dto.divisionmember.DivisionMemberWithTimeDto;
import com.eximbay.okr.entity.Division;
import com.eximbay.okr.entity.DivisionMember;
import com.eximbay.okr.entity.DivisionMemberId;
import com.eximbay.okr.entity.Member;
import com.eximbay.okr.repository.DivisionMemberRepository;
import com.eximbay.okr.service.Interface.IDivisionMemberService;
import com.eximbay.okr.service.Interface.IMemberService;
import com.eximbay.okr.service.specification.DivisionMemberQuery;
import lombok.AllArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class DivisionMemberServiceImpl implements IDivisionMemberService {
    private final DivisionMemberRepository divisionMemberRepository;
    private final DivisionMemberQuery divisionMemberQuery;
    private final IMemberService memberService;
    private final MapperFacade mapper;

    @Override
    public List<DivisionMemberDto> findAll() {
        List<DivisionMember> divisionMembers = divisionMemberRepository.findAll();
        return mapper.mapAsList(divisionMembers, DivisionMemberDto.class);
    }

    @Override
    public Optional<DivisionMemberDto> findById(DivisionMemberId id) {
        Optional<DivisionMember> divisionMember = divisionMemberRepository.findById(id);
        return divisionMember.map(m-> mapper.map(m, DivisionMemberDto.class));
    }

    @Override
    public void remove(DivisionMemberDto DivisionMemberDto) {
        DivisionMember divisionMember = mapper.map(DivisionMemberDto, DivisionMember.class);
        divisionMemberRepository.delete(divisionMember);
    }

    @Override
    public DivisionMemberDto save(DivisionMemberDto divisionMemberDto) {
        DivisionMember divisionMember = new DivisionMember();
        divisionMember.setDivisionMemberId(divisionMemberDto.getDivisionMemberId());
        divisionMember.setApplyEndDate(divisionMemberDto.getApplyEndDate());
        divisionMember.setJustification(divisionMemberDto.getJustification());
        divisionMember = divisionMemberRepository.save(divisionMember);
        DivisionMemberDto result = new DivisionMemberDto(divisionMember.getDivisionMemberId(),divisionMember.getApplyEndDate(), divisionMember.getJustification());
        return result;
    }

    private void mapDivisionMemberId(DivisionMemberDto dto, Division division, Member member, String applyBeginDate){
        dto.getDivisionMemberId().setDivision(division);
        dto.getDivisionMemberId().setMember(member);
        dto.getDivisionMemberId().setApplyBeginDate(applyBeginDate);
    }

    @Override
    public void removeDivisionMember(RemoveDivisionMemberFormDto dto) {
        DivisionMemberDto divisionMemberDto = mapper.map(dto, DivisionMemberDto.class);
        mapDivisionMemberId(divisionMemberDto, dto.getDivision(), dto.getMember(), dto.getApplyBeginDate());
        divisionMemberDto.setApplyEndDate(divisionMemberDto.getApplyEndDate().replace("-",""));
        save(divisionMemberDto);
    }

    @Override
    public void addDivisionMember(AddDivisionMemberFormDto dto) {
        List<DivisionMember> divisionMembers = findActiveInFutureByMember(dto.getMember().getMemberSeq());
        DivisionMemberDto divisionMemberDto = new DivisionMemberDto();
        if (divisionMembers.size() > 0) {
            divisionMemberDto.getDivisionMemberId().setMember(divisionMembers.get(0).getDivisionMemberId().getMember());
            divisionMemberDto.getDivisionMemberId().setDivision(divisionMembers.get(0).getDivisionMemberId().getDivision());
            divisionMemberDto.getDivisionMemberId().setApplyBeginDate(dto.getApplyBeginDate().replace("-",""));
            divisionMemberRepository.deleteAll(divisionMembers);
        } else {
            divisionMemberDto = mapper.map(dto, DivisionMemberDto.class);
            mapDivisionMemberId(divisionMemberDto, dto.getDivision(), dto.getMember(), dto.getApplyBeginDate().replace("-",""));
        }
        divisionMemberDto.setApplyEndDate(AppConst.DEFAULT_APPLY_END_DATE);
        divisionMemberDto.setJustification(AppConst.DEFAULT_ADD_DIVISION_MEMBER_JUSTIFICATION);
        save(divisionMemberDto);
    }

    @Override
    public List<MemberDto> findAvailableMemberToAddToDivision(DivisionDto divisionDto) {
        List<MemberDto> currentMembers = findActiveMembersOfDivision(divisionDto);
        List<MemberDto> allActiveLeadOrDirectorMembers = memberService.findActiveLeadOrDirectorMembers();
        allActiveLeadOrDirectorMembers.removeAll(currentMembers);
        return allActiveLeadOrDirectorMembers;
    }

    @Override
    public List<MemberDto> findActiveMembersOfDivision(DivisionDto divisionDto) {
        List<DivisionMember> divisionMembers = divisionMemberRepository.findAll(
                divisionMemberQuery.findByDivisionSeq(divisionDto.getDivisionSeq())
                        .and(divisionMemberQuery.findCurrentlyValid())
                        .and(divisionMemberQuery.findActiveMemberOnly())
        );
        List<MemberDto> currentMembers = mapper.mapAsList(divisionMembers.stream().map(m->m.getDivisionMemberId().getMember()).collect(Collectors.toList()), MemberDto.class);
        return currentMembers;
    }

    @Override
    public List<DivisionMemberWithTimeDto> findActiveMembersOfDivisionWithTime(DivisionDto divisionDto) {
        List<DivisionMember> divisionMembers = divisionMemberRepository.findAll(
                divisionMemberQuery.findByDivisionSeq(divisionDto.getDivisionSeq())
                        .and(divisionMemberQuery.findCurrentlyValid())
                        .and(divisionMemberQuery.findActiveMemberOnly())
        );
        List<DivisionMemberWithTimeDto> result = new ArrayList<>();
        for (DivisionMember divisionMember: divisionMembers){
            DivisionMemberWithTimeDto item = new DivisionMemberWithTimeDto();
            item.setDivision(divisionDto);
            item.setMember(mapper.map(divisionMember.getDivisionMemberId().getMember(), MemberDto.class));
            item.setApplyBeginDate(divisionMember.getDivisionMemberId().getApplyBeginDate());
            item.setApplyEndDate(divisionMember.getApplyEndDate());
            result.add(item);
        }
        return result;
    }

    @Override
    public List<DivisionMember> findActiveInFutureByMember(Integer memberSeq){
        List<DivisionMember> divisionMembers = divisionMemberRepository.findAll(
                divisionMemberQuery.findByMemberSeq(memberSeq)
                        .and(divisionMemberQuery.isActiveInFuture())
        );
        return divisionMembers;
    }
}
