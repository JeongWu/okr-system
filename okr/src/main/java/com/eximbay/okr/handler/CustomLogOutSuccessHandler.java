package com.eximbay.okr.handler;

import com.eximbay.okr.enumeration.LogType;
import com.eximbay.okr.service.Interface.IAuditLogService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@EqualsAndHashCode(callSuper = true)
@Component
@Data
@AllArgsConstructor
public class CustomLogOutSuccessHandler extends
        SimpleUrlLogoutSuccessHandler implements LogoutSuccessHandler {
    private final IAuditLogService auditLogService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        auditLogService.log(LogType.SIGNOUT, request, authentication, null, false);
        super.onLogoutSuccess(request, response, authentication);
    }
}
