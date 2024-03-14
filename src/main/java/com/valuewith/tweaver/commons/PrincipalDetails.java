package com.valuewith.tweaver.commons;

import com.valuewith.tweaver.constants.ErrorCode;
import com.valuewith.tweaver.exception.CustomAuthException;
import com.valuewith.tweaver.member.entity.Member;
import java.util.Collection;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

@AllArgsConstructor
@RequiredArgsConstructor
public class PrincipalDetails implements UserDetails, OAuth2User {

  private Member member;
  private Map<String, Object> attributes;
  private Collection<GrantedAuthority> authorities;  // member의 권한입니다. 현재는 사용하지 않습니다.

  // 일반 로그인 생성자
  public PrincipalDetails(Member member) {
    this.member = member;
  }

  //OAuth 로그인 생성자
  public PrincipalDetails(Member member, Map<String, Object> attributes) {
    this.member = member;
    this.attributes = attributes;
  }

  // OAuth2User 인터페이스 메소드
  @Override
  public Map<String, Object> getAttributes() {
    return this.attributes;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.authorities;
  }

  @Override
  public String getPassword() {
    return member.getPassword();
  }

  @Override
  public String getUsername() {
    if (member == null) {
      throw new CustomAuthException(ErrorCode.INVALID_CUSTOM_ID);  // [002] 401 unauthorized
    }
    return member.getEmail();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  // OAuth2 인터페이스 메소드
  @Override
  public String getName() {
    if (member == null) {
      throw new CustomAuthException(ErrorCode.INVALID_CUSTOM_NAME);  // [003] 401 unauthorized
    }
    return member.getEmail();
  }

  public Long getId() {
    if (member == null) {
      throw new CustomAuthException(ErrorCode.INVALID_CUSTOM_ID);  // [001] 401 unauthorized
    }
    return member.getMemberId();
  }
}