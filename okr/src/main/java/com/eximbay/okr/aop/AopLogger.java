package com.eximbay.okr.aop;

import com.eximbay.okr.enumeration.LogType;
import com.eximbay.okr.repository.AuditLogRepository;
import com.eximbay.okr.service.Interface.IAuditLogService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.Advised;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
@RequiredArgsConstructor
public class AopLogger {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final IAuditLogService auditLogService;
    private final String saveMethod = "save";
    private final String removeMethod = "remove";

    @Pointcut("within(@org.springframework.stereotype.Repository *)" +
            " || within(@org.springframework.stereotype.Service *)" +
            " || within(@org.springframework.stereotype.Controller *)")
    public void springBeanPointcut() {}

    @Pointcut("within(com.eximbay.okr.controller..*)" +
            " || within(com.eximbay.okr.service..*)" +
            " || within(com.eximbay.okr.repository..*)")
    public void applicationPackagePointcut() {}

    @Pointcut("within(@org.springframework.stereotype.Controller *)" +
            " && @annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void springControllerGetMappingPointCut() {}

    @Pointcut("execution(* com.eximbay.okr.repository..*(..))")
    public void springRepositoryPointCut() {}

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)" +
            " || within(@org.springframework.stereotype.Controller *)")
    public void springControllerPointCut() {}

    @AfterThrowing(pointcut = "applicationPackagePointcut() && springBeanPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        log.error("Exception {} in {}.{}() with cause = {} and message = {}",
                        e.getClass().getSimpleName(),
                        joinPoint.getSignature().getDeclaringTypeName(),
                        joinPoint.getSignature().getName(),
                        e.getCause() != null ? e.getCause().getClass().getSimpleName() : "NULL",
                        e.getCause() != null ? e.getCause().getMessage(): "NULL");
    }

    @Around("applicationPackagePointcut() && springBeanPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        if (log.isDebugEnabled()) {
            log.debug("Enter: {}.{}() with argument[s] = {}",
                        joinPoint.getSignature().getDeclaringTypeName(),
                        joinPoint.getSignature().getName(),
                        Arrays.toString(joinPoint.getArgs()));
        }
        try {
            Object result = joinPoint.proceed();
            if (log.isDebugEnabled()) {
                log.debug("Exit: {}.{}() with result = {}",
                        joinPoint.getSignature().getDeclaringTypeName(),
                        joinPoint.getSignature().getName(),
                        result);
            }
            return result;
        } catch (IllegalArgumentException e) {
            log.error("Illegal argument: {} in {}.{}()",
                    Arrays.toString(joinPoint.getArgs()),
                    joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName());
            throw e;
        }
    }

    @Before("springControllerGetMappingPointCut()")
    public void pageAccessLog(JoinPoint joinPoint) {
        auditLogService.log(LogType.PAGE, getRequest(), getAuthentication(), null, false);
    }

    @Before("springRepositoryPointCut()")
    public void dataChangeLog(JoinPoint joinPoint) throws InterruptedException {
        Advised advised = (Advised) joinPoint.getTarget();
        if (advised.getProxiedInterfaces()[0].equals(AuditLogRepository.class)) return;
        String method = joinPoint.getSignature().getName();
        if (!method.equals(saveMethod) && !method.equals(removeMethod)) return;
        auditLogService.log(LogType.DATA, getRequest(), getAuthentication(), joinPoint, false);
    }

    @Before("springControllerPointCut()")
    public void dataAccessLog(JoinPoint joinPoint) throws InterruptedException {
        auditLogService.log(LogType.DATA, getRequest(), getAuthentication(), joinPoint, false);
    }

    private HttpServletRequest getRequest(){
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }

    private Authentication getAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

}
