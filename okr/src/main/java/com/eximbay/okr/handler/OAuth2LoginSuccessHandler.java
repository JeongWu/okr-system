package com.eximbay.okr.handler;

import com.eximbay.okr.config.security.MyUserDetails;
import com.eximbay.okr.config.security.MyUserDetailsService;
import com.eximbay.okr.dto.MemberDto;
import com.eximbay.okr.enumeration.LogType;
import com.eximbay.okr.service.Interface.IAuditLogService;
import com.eximbay.okr.service.Interface.IMemberService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final IMemberService memberService;
    private final MyUserDetailsService myUserDetailsService;
    private final IAuditLogService auditLogService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        Optional<MemberDto> memberDto = memberService.findByEmail(oAuth2User.getAttribute("email"));
        if (memberDto.isEmpty()){
            SecurityContextHolder.getContext().setAuthentication(null);
            response.sendRedirect("/login");
        } else {
            MyUserDetails userDetails = new MyUserDetails(memberDto.get());
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            auditLogService.log(LogType.SIGNIN, request, usernamePasswordAuthenticationToken, null, false);
            response.sendRedirect("/");
        }
    }
}
