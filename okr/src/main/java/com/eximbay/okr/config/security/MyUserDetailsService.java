package com.eximbay.okr.config.security;

import com.eximbay.okr.dto.MemberDto;
import com.eximbay.okr.service.Interface.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    IMemberService memberService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<MemberDto> user = memberService.findByEmail(email);
        user.orElseThrow(()-> new UsernameNotFoundException("Not found: "+ email));
        return new MyUserDetails(user.get());
    }
}
