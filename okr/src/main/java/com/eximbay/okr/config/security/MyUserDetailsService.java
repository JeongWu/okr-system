package com.eximbay.okr.config.security;

import com.eximbay.okr.dto.member.MemberDto;
import com.eximbay.okr.service.Interface.IMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final IMemberService memberService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<MemberDto> user = memberService.findByEmail(email);
        user.orElseThrow(()-> new UsernameNotFoundException("Not found: "+ email));
        return new MyUserDetails(user.get());
    }
}
