package com.valuewith.tweaver.commons.security;

import com.valuewith.tweaver.commons.PrincipalDetails;
import com.valuewith.tweaver.commons.security.service.TokenService;
import com.valuewith.tweaver.member.entity.Member;
import com.valuewith.tweaver.member.repository.MemberRepository;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  public static final String TOKEN_HEADER = "Authorization";

  private final TokenService tokenService;
  private final MemberRepository memberRepository;

  private final GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    try {
      // 리프레시 토큰이 유효한지 검증합니다.
      String refreshToken = tokenService
          .parseRefreshToken(request)
          .filter(tokenService::isValidToken)
          .orElse(null);

    /*
      헤더에 리프레시 토큰이 존재 -> 사용자 AccessToken 만료
      받은 리프레시 토큰을 DB와 비교 후 재발급 진행
     */
      if (refreshToken != null) {
        reissueAccessTokenAfterRefreshToken(response, refreshToken);
        return;
      }

    /*
      리프레시 토큰이 없거나 유효하지 않다면 사용자 AccessToken 검사가 필요하다.
      AccessToken이 없거나 유효하지 않다면 -> 403
      AccessToken이 있고 유효하다면 -> 200
     */
      String headerToken = getAccessTokenFromRequest(request);
      if (headerToken == null) {
        filterChain.doFilter(request, response);
        return;
      }
      authenticateAccessToken(headerToken);
    } catch (Exception e) {
      request.setAttribute("exception", e);
    }
    filterChain.doFilter(request, response);
  }

  private void checkTokenValidity(String token) {
    tokenService.checkTokenValidity(token);
  }

  public void reissueAccessTokenAfterRefreshToken(HttpServletResponse response,
      String refreshToken) {
    memberRepository.findByRefreshToken(refreshToken).ifPresent(member ->
        {
          String newRefreshToken = reissueRefreshToken(member);
          tokenService.sendAccessTokenAndRefreshToken(
              response,
              tokenService.createAccessToken(member.getEmail(), member.getMemberId()),
              newRefreshToken);
        }
    );
  }

  public void authenticateAccessToken(String trimmedAccessToken) {
    checkTokenValidity(trimmedAccessToken);
    String email = tokenService.getMemberEmailForFilter(trimmedAccessToken);
    memberRepository.findByEmail(email).ifPresent(this::saveAuthentication);
  }

  public String getAccessTokenFromRequest(HttpServletRequest request) {
    String bearerToken = request.getHeader(TOKEN_HEADER);
    if (bearerToken == null) {
      log.warn("getAccessTokenFromRequest: [Nothing] 토큰 없음");
      return null;
    }
    return tokenService.parseAccessToken(request);
  }

  private String reissueRefreshToken(Member member) {
    String newRefreshToken = tokenService.createRefreshToken();
    member.updateRefreshToken(newRefreshToken);
    memberRepository.saveAndFlush(member);
    return newRefreshToken;
  }

  public void saveAuthentication(Member member) {
    PrincipalDetails principalDetails = new PrincipalDetails(member);
    Authentication authentication = new UsernamePasswordAuthenticationToken(
        principalDetails, "", authoritiesMapper.mapAuthorities(principalDetails.getAuthorities())
    );
    SecurityContextHolder.getContext().setAuthentication(authentication);
  }
}
