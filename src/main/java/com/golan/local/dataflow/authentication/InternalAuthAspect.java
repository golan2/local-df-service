package com.golan.local.dataflow.authentication;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import static com.golan.local.dataflow.authentication.AuthenticationConfig.INTERNAL_TOKEN_HEADER;


@SuppressWarnings("unused") //methods below are called by AspectJ so IntelliJ doesn't see it
@Slf4j
@Aspect
@Component
public class InternalAuthAspect {
    private final AuthenticationConfig authenticationConfig;

    public InternalAuthAspect(AuthenticationConfig authenticationConfig) {
        this.authenticationConfig = authenticationConfig;
        log.info("AuthenticationConfig: " + authenticationConfig);
    }

    @SuppressWarnings("EmptyMethod")
    @Pointcut("execution(* (@com.golan.local.dataflow.authentication.InternalApi *).*(..))")
    public void authorizeInternalClass() {}

    @Before("authorizeInternalClass()")
    void authorize() throws InternalApiUnauthorizedException {
        if (!authenticationConfig.isEnabled()) return;

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        if (request == null) {
            log.debug("Internal API call without a request bound to it");
            throw new InternalApiUnauthorizedException();
        }

        final String tokenOnRequest = request.getHeader(INTERNAL_TOKEN_HEADER);
        if (tokenOnRequest == null) {
            log.debug("Missing internal token header: " + INTERNAL_TOKEN_HEADER);
            throw new InternalApiUnauthorizedException();
        }
        if (!this.authenticationConfig.getInternalToken().equals(tokenOnRequest)) {
            log.debug("Token mismatch (The internal token on the request is: "+tokenOnRequest+")");
            throw new InternalApiUnauthorizedException();
        }

    }

}
