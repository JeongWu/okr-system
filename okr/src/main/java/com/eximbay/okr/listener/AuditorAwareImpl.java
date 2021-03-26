package com.eximbay.okr.listener;

import com.eximbay.okr.dto.MemberDto;
import com.eximbay.okr.service.Interface.IMemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {
    @Autowired
    private IMemberService memberService;

    @Override
    public Optional<String> getCurrentAuditor() {
        Optional<MemberDto> currentMember = memberService.getCurrentMember();
        return currentMember.map(MemberDto::getMemberId).or(()-> Optional.of("system"));
    }
}
