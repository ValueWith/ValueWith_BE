package com.valuewith.tweaver.auth;

import com.valuewith.tweaver.constants.ErrorCode;
import com.valuewith.tweaver.exception.CustomAuthException;
import java.lang.annotation.Annotation;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.expression.BeanResolver;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class CustomAuthenticationPrincipalArgumentResolver implements
    HandlerMethodArgumentResolver {

  private ExpressionParser parser = new SpelExpressionParser();
  private BeanResolver beanResolver;

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return findMethodAnnotation(CustomAuthPrincipal.class, parameter) != null;
  }

  @Override
  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null) {
      throw new CustomAuthException(ErrorCode.NO_PRINCIPAL);  // [100] 401 unauthorized
    }
    Object principal = authentication.getPrincipal();
    CustomAuthPrincipal annotation = findMethodAnnotation(CustomAuthPrincipal.class, parameter);
    String expressionToParse = annotation.expression();
    if (StringUtils.hasLength(expressionToParse)) {
      StandardEvaluationContext context = new StandardEvaluationContext();
      context.setRootObject(principal);
      context.setVariable("this", principal);
      context.setBeanResolver(this.beanResolver);
      Expression expression = this.parser.parseExpression(expressionToParse);
      principal = expression.getValue(context);
    }
    if (principal != null && !ClassUtils.isAssignable(parameter.getParameterType(),
        principal.getClass())) {
      if (annotation.errorOnInvalidType()) {
        throw new ClassCastException(
            principal + " is not assignable to " + parameter.getParameterType());
      }
      throw new CustomAuthException(ErrorCode.INVALID_USER_DETAILS);  // [200] 401 unauthorized
    }
    return principal;
  }

  private <T extends Annotation> T findMethodAnnotation(Class<T> annotationClass,
      MethodParameter parameter) {
    T annotation = parameter.getParameterAnnotation(annotationClass);
    if (annotation != null) {
      return annotation;
    }
    Annotation[] annotationsToSearch = parameter.getParameterAnnotations();
    for (Annotation toSearch : annotationsToSearch) {
      annotation = AnnotationUtils.findAnnotation(toSearch.annotationType(), annotationClass);
      if (annotation != null) {
        return annotation;
      }
    }
    return null;
  }
}
