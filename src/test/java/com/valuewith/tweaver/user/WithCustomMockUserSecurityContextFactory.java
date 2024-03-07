package com.valuewith.tweaver.user;

import com.valuewith.tweaver.commons.PrincipalDetails;
import com.valuewith.tweaver.member.entity.Member;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithCustomMockUserSecurityContextFactory implements
    WithSecurityContextFactory<WithCustomMockUser> {


  @Override
  public SecurityContext createSecurityContext(WithCustomMockUser annotation) {
    SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
    Member member = Member.builder()
        .email(annotation.username())
        .password(annotation.password())
        .build();
    PrincipalDetails principalDetails = new PrincipalDetails(member);

    Authentication token =
        new UsernamePasswordAuthenticationToken(principalDetails,
            principalDetails.getPassword(),
            principalDetails.getAuthorities());
    securityContext.setAuthentication(token);

    return securityContext;
  }
}
