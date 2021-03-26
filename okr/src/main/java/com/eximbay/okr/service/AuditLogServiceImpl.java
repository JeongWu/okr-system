package com.eximbay.okr.service;

import com.eximbay.okr.config.security.MyUserDetails;
import com.eximbay.okr.constant.ErrorMessages;
import com.eximbay.okr.dto.MemberDto;
import com.eximbay.okr.dto.auditLog.AuditLogDto;
import com.eximbay.okr.dto.auditLog.AuditLogsDatatablesInput;
import com.eximbay.okr.entity.AuditLog;
import com.eximbay.okr.enumeration.LogType;
import com.eximbay.okr.exception.UserException;
import com.eximbay.okr.model.ComboBoxModel;
import com.eximbay.okr.model.auditLog.AuditLogsModel;
import com.eximbay.okr.repository.AuditLogRepository;
import com.eximbay.okr.service.Interface.IAuditLogService;
import com.eximbay.okr.service.Interface.IMemberService;
import com.eximbay.okr.service.specification.AuditLogQuery;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import ma.glasnost.orika.MapperFacade;
import org.aspectj.lang.JoinPoint;
import org.springframework.aop.framework.Advised;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Data
@AllArgsConstructor
@Transactional
public class AuditLogServiceImpl implements IAuditLogService {
    private final AuditLogRepository auditLogRepository;
    private final AuditLogQuery auditLogQuery;
    private final IMemberService memberService;
    private final MapperFacade mapper;

    @Override
    public List<AuditLogDto> findAll() {
        List<AuditLog> auditLogs = Lists.newArrayList(auditLogRepository.findAll());
        return mapper.mapAsList(auditLogs, AuditLogDto.class);
    }

    @Override
    public Optional<AuditLogDto> findById(Integer id) {
        Optional<AuditLog> auditLog = auditLogRepository.findById(id);
        Optional<AuditLogDto> auditLogDto = auditLog.map(m-> mapper.map(m, AuditLogDto.class));
        return auditLogDto;
    }

    @Override
    public void remove(AuditLogDto auditLogDto) {
        AuditLog auditLog = mapper.map(auditLogDto, AuditLog.class);
        auditLogRepository.delete(auditLog);
    }

    @Override
    public AuditLogDto save(AuditLogDto auditLogDto) {
        AuditLog auditLog = mapper.map(auditLogDto, AuditLog.class);
        auditLog = auditLogRepository.save(auditLog);
        return mapper.map(auditLog, AuditLogDto.class);
    }

    @Override
    public AuditLogsModel buildAuditLogsModel() {
        AuditLogsModel auditLogsModel = new AuditLogsModel();
        List<ComboBoxModel> logTypes = List.of(
                new ComboBoxModel("SIGN IN", "SIGNIN"),
                new ComboBoxModel("SIGN OUT", "SIGNOUT"),
                new ComboBoxModel("PAGE", "PAGE"),
                new ComboBoxModel("DATA", "DATA")
        );
        auditLogsModel.setLogTypes(logTypes);
        return auditLogsModel;
    }

    @Override
    public DataTablesOutput<AuditLog> getDataForDatatables(AuditLogsDatatablesInput input) {
        DataTablesOutput<AuditLog> output = auditLogRepository
                .findAll(input,
                        auditLogQuery.buildQueryForDatatables(input)
                );
        return output;
    }

    @Override
    public void log(LogType logType, HttpServletRequest request, Authentication authentication, JoinPoint joinPoint, boolean isChangeData) {
        if (authentication == null || !(authentication.getPrincipal() instanceof MyUserDetails)) return;
        MemberDto currentMember = ((MyUserDetails) authentication.getPrincipal()).getMemberDto();
        try {
            if (logType.equals(LogType.SIGNIN)) signInLog(request, currentMember);
            if (logType.equals(LogType.SIGNOUT)) signOutLog(request, currentMember);
            if (logType.equals(LogType.PAGE)) pageLog(request, currentMember);
            if (logType.equals(LogType.DATA)) {
                if (isChangeData) dataChangeLog(request, currentMember, joinPoint);
                else dataAccessLog(request, currentMember, joinPoint);
            }
        } catch (Exception e){
            throw new UserException(ErrorMessages.canNotLog + " with type " + logType + "with jointPoint " + joinPoint.toString());
        }

    }

    private AuditLog initAuditLog(LogType logType, MemberDto memberDto, HttpServletRequest request){
        return  AuditLog
                .builder()
                .logType(logType)
                .email(memberDto.getEmail())
                .name(memberDto.getName())
                .accessIp(request.getRemoteAddr())
                .build();
    }

    private void signInLog(HttpServletRequest request, MemberDto memberDto){
        AuditLog auditLog = initAuditLog(LogType.SIGNIN, memberDto, request);
        auditLogRepository.save(auditLog);
    }

    private void signOutLog(HttpServletRequest request, MemberDto memberDto){
        AuditLog auditLog = initAuditLog(LogType.SIGNOUT, memberDto, request);
        auditLogRepository.save(auditLog);
    }

    private void pageLog(HttpServletRequest request, MemberDto memberDto){
        AuditLog auditLog = initAuditLog(LogType.PAGE, memberDto, request);
        auditLog.setTarget(request.getRequestURI());
        auditLog.setParameter(request.getQueryString());
        auditLogRepository.save(auditLog);
    }

    private void dataChangeLog(HttpServletRequest request, MemberDto memberDto, JoinPoint joinPoint){
        AuditLog auditLog = initAuditLog(LogType.DATA, memberDto, request);

        Advised advised = (Advised) joinPoint.getTarget();
        String className = advised.getProxiedInterfaces()[0].getSimpleName();

        String method = joinPoint.getSignature().getName();
        String parameters = shortenParameters(joinPoint);

        auditLog.setTarget(className + "::" + method);
        auditLog.setParameter(parameters);
        auditLogRepository.save(auditLog);
    }

    private void dataAccessLog(HttpServletRequest request, MemberDto memberDto, JoinPoint joinPoint){
        AuditLog auditLog = initAuditLog(LogType.DATA, memberDto, request);

        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
        String method = joinPoint.getSignature().getName();
        String parameters = shortenParameters(joinPoint);

        auditLog.setTarget(className + "::" + method);
        auditLog.setParameter(parameters);
        auditLogRepository.save(auditLog);
    }

    private String shortenParameters(JoinPoint joinPoint){
        String parameters = Arrays.toString(joinPoint.getArgs());
        if (parameters.length() < 1000) return parameters;
        else return Arrays.stream(joinPoint.getArgs()).map(m->{
            if (m instanceof Model) {
                return ((Model) m).asMap().values().stream().map(value -> value.getClass().getSimpleName()).collect(Collectors.joining(","));
            }
                return m.getClass().getSimpleName();
            }).collect(Collectors.joining(";"));
    }
}
