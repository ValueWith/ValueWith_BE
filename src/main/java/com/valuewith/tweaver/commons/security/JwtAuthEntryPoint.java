package com.valuewith.tweaver.commons.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Component
@Slf4j
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {

  private final HandlerExceptionResolver resolver;

  public JwtAuthEntryPoint(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
    this.resolver = resolver;
  }

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
    log.error("resolver - 인증 에러 발생");
    try {
      resolver.resolveException(request, response, null,
          (Exception) request.getAttribute("exception"));
    } catch (Exception e) {
          response.setStatus(888);
    }
  }
}
