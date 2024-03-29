package com.fruntier.fruntier;

import com.fruntier.fruntier.user.domain.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.Token;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fruntier.fruntier.JwtTokenService;
import com.fruntier.fruntier.user.exceptions.TokenValidationException;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.Optional;

@Aspect
@Component
@Slf4j
public class TokenValidationAspect {

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private HttpServletRequest request;

    @Pointcut("@annotation(RequireTokenValidation)")
    public void methodsRequiringTokenValidation() {}

    @Before("methodsRequiringTokenValidation()")
    public void validateToken() throws TokenValidationException {
        String token = Arrays.stream(Optional.ofNullable(request.getCookies()).orElse(new Cookie[0]))
                .filter(c -> "authToken".equals(c.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new TokenValidationException("Missing or invalid authentication token"));

        User validatedUser = jwtTokenService.validateTokenReturnUser(token);

        log.info("user={}", validatedUser);

        request.setAttribute("validatedUser", validatedUser);
    }
}
