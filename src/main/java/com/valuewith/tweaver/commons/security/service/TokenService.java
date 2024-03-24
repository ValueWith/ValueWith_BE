package com.valuewith.tweaver.commons.security.service;


import com.valuewith.tweaver.constants.ErrorCode;
import com.valuewith.tweaver.exception.CustomAuthException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Date;
import java.util.Optional;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Slf4j
@Component
@RequiredArgsConstructor
@Getter
public class TokenService {

  private static final Long ACCESS_TOKEN_VALID_TIME = 1000L * 60L * 60L;  // 1시간
  private static final Long REFRESH_TOKEN_VALID_TIME = 1000L * 60L * 60L * 24L * 7L;  // 7일
  private static final String ACCESS_SUBJECT = "Access";
  private static final String REFRESH_SUBJECT = "Refresh";
  private static final String CLAIM_EMAIL = "email";
  private static final String CLAIM_MEMBER_ID = "memberId";
  private static final String BEARER = "Bearer ";

  private final String accessHeader = "Authorization";
  private final String refreshHeader = "RefreshToken";

  @Value("${jwt-secret-key}")
  private String secretKey;

  private final PrincipalService principalService;

  /*
  Access 토큰을 생성합니다. 페이로드에 들어갈 기본적인 정보는 다음과 같습니다. 1. subject: Access 2. expiration: 1시간 3. claim:
  email 변경사항 있을시에 claims에 put(클레임 이름, 값) 형식으로 추가해주세요. 클레임 이름은 상수로 추가해주세요. (파싱에서 사용)
  */
  public String createAccessToken(String email, Long memberId) {
    Claims claims = Jwts.claims().setSubject(ACCESS_SUBJECT);
    claims.put(CLAIM_EMAIL, email);
    claims.put(CLAIM_MEMBER_ID, memberId);

    Date now = new Date();
    Date expiredDate = new Date(now.getTime() + ACCESS_TOKEN_VALID_TIME);

    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(expiredDate)
        .signWith(SignatureAlgorithm.HS512, this.secretKey)  // HMAC 기반 인증
        .compact();
  }

  /*
  Refresh 토큰을 생성합니다. 페이로드에 들어갈 기본적인 정보는 다음과 같습니다. 1. subject: Refresh 2. expiration: 7일(1주)
  Refresh 토큰에는 별도의 클레임이 들어가지 않습니다.
  */
  public String createRefreshToken() {
    Claims claims = Jwts.claims().setSubject(REFRESH_SUBJECT);
    Date now = new Date();
    Date expiredDate = new Date(now.getTime() + REFRESH_TOKEN_VALID_TIME);
    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(expiredDate)
        .signWith(SignatureAlgorithm.HS512, this.secretKey)  // HMAC 기반 인증
        .compact();
  }

  /**
   * 토큰의 이메일 정보를 가져옵니다.
   * 주의사항: JwtAuthenticationFilter 에서 사용합니다. 이외의 곳에선 사용하지 말아주세요
   *
   * @param token Bearer가 포함된 토큰
   * @return token에 포함된 Email 정보
   */
  public String getMemberEmailForFilter(String token) {
    log.info("getMemberEmailForFilter - Authorization: " + token);
    String trimmedToken = resolveTokenOnlyAtTokenService(token);
    return this.parseClaimsForFilter(trimmedToken).get(CLAIM_EMAIL).toString();
  }

  /**
   * 토큰의 이메일 정보를 반환합니다.
   * (@CustomAuthPrincipal 어노테이션 사용을 권장합니다.)
   *
   * @param token Bearer가 포함된 토큰
   * @return token에 포함된 Email 정보
   */
  public String getMemberEmail(String token) {
    log.info("getMemberEmail - Authorization: " + token);
    String trimmedToken = resolveTokenOnlyAtTokenService(token);
    return this.parseClaims(trimmedToken).get(CLAIM_EMAIL).toString();
  }

  /**
   * 토큰의 멤버 id 정보를 반환합니다.
   * (@CustomAuthPrincipal 어노테이션 사용을 권장합니다.)
   *
   * @param token Bearer가 포함된 토큰
   * @return token에 포함된 Email 정보
   */
  public Long getMemberId(String token) {
    log.info("getMemberId - Authorization: " + token);
    String trimmedToken = resolveTokenOnlyAtTokenService(token);
    return Long.parseLong(this.parseClaims(trimmedToken).get(CLAIM_MEMBER_ID).toString());
  }

  public String resolveTokenOnlyAtTokenService(String token) {
    if (ObjectUtils.isEmpty(token)) {
      return null;
    }
    if (!token.startsWith(BEARER)) {
      return null;
    }
    return token.replace(BEARER, "");
  }

  /**
   * 주어진 토큰의 claims에 포함된 만료시간이 남아있다면 true를 반환합니다.
   * Jwt가 반환하는 예외를 로그로 남깁니다.
   *
   * @param trimmedToken 검증할 token
   * @return 유효한 토큰이면 true, 만료된 토큰이면 false
   */
  public boolean isValidToken(String trimmedToken) {
    try {
      Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(trimmedToken);
      return true;
    } catch (MalformedJwtException e) {
      log.error("isValidToken: [MalFormed] 잘못된 인증 정보");
    } catch (SignatureException e) {
      log.error("isValidToken: [Signature] 잘못된 접근");
    } catch (ExpiredJwtException e) {
      log.error("isValidToken: [Expired] 만료된 접근");
    } catch (UnsupportedJwtException e) {
      log.error("isValidToken: [Unsupported] 잘못된 접근");
    } catch (IllegalArgumentException e) {
      log.error("isValidToken: [Illegal] 잘못된 인증 정보");
    }
    return false;
  }

  /**
   * 주어진 토큰을 해독하여 claim값들을 반환합니다.
   * 사용시 클라이언트에 CustomAuthException을 던집니다.
   *
   * @param trimmedToken Bearer가 제거된 토큰
   * @return Jwt가 가지고 있는 Claims 정보
   * @throws CustomAuthException Jwt 예외와 동일
   */
  private Claims parseClaims(String trimmedToken) {
    try {
      return Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(trimmedToken).getBody();
    } catch (MalformedJwtException e) {
      log.error("parseClaims: [MalFormed] 잘못된 인증 정보");
      throw new CustomAuthException(ErrorCode.INVALID_JWT_M);
    } catch (SignatureException e) {
      log.error("parseClaims: [Signature] 잘못된 접근");
      throw new CustomAuthException(ErrorCode.INVALID_JWT_S);
    } catch (ExpiredJwtException e) {
      log.error("parseClaims: [Expired] 만료된 접근");
      throw new CustomAuthException(ErrorCode.INVALID_JWT_E);
    } catch (UnsupportedJwtException e) {
      log.error("parseClaims: [Unsupported] 잘못된 접근");
      throw new CustomAuthException(ErrorCode.INVALID_JWT_U);
    } catch (IllegalArgumentException e) {
      log.error("parseClaims: [Illegal] 잘못된 인증 정보");
      throw new CustomAuthException(ErrorCode.INVALID_JWT_I);
    }
  }

  /**
   * JwtAuthenticationFileter에서 사용됩니다.
   * 주어진 토큰을 해독하여 claim값들을 반환합니다.
   * 사용시 Jwt 예외 로그를 남깁니다.
   *
   * @param trimmedToken Bearer가 제거된 토큰
   * @return Jwt가 가지고 있는 Claims 정보
   */
  private Claims parseClaimsForFilter(String trimmedToken) {
    try {
      return Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(trimmedToken).getBody();
    } catch (ExpiredJwtException e) {
      log.error("parseClaimsForFilter: [Expired] 만료된 접근");
      return e.getClaims();
    }
  }

  /*
   헤더에 Authorization: "Bearer {token}" 형식으로 오게 됩니다. 이때 "Bearer "를 지우고 "{token}" 값으로 파싱합니다.
   */
  public String parseAccessToken(HttpServletRequest request) {
    String fullToken = request.getHeader(accessHeader);
    return resolveTokenOnlyAtTokenService(fullToken);
  }

  public Optional<String> parseRefreshToken(HttpServletRequest request) {
    return Optional.ofNullable(request.getHeader(refreshHeader))
        .filter(refresh -> refresh.startsWith(BEARER))
        .map(refresh -> refresh.replace(BEARER, ""));
  }

  // 재발급된 Access 토큰을 헤더에 넣어서 보냅니다.
  public void sendAccessToken(HttpServletResponse response, String accessToken) {
    response.setStatus(HttpServletResponse.SC_OK);
    response.setHeader(accessHeader, accessToken);
  }

  // Access 토큰, Refresh 토큰을 모두 헤더에 넣어서 보냅니다.
  public void sendAccessTokenAndRefreshToken(HttpServletResponse response,
      String accessToken, String refreshToken) {
    response.setStatus(HttpServletResponse.SC_OK);

    setAccessTokenToHeader(response, BEARER + accessToken);
    setRefreshTokenToHeader(response, BEARER + refreshToken);
    setRefreshTokenToCookie(response, refreshToken);
  }

  public void setAccessTokenToHeader(HttpServletResponse response, String accessToken) {
    response.setHeader(accessHeader, accessToken);
  }

  public void setRefreshTokenToHeader(HttpServletResponse response, String refreshToken) {
    response.setHeader(refreshHeader, refreshToken);
  }

  public void setRefreshTokenToCookie(HttpServletResponse response, String refreshToken) {
    Cookie cookie = new Cookie(refreshHeader, refreshToken);
    cookie.setMaxAge(Math.toIntExact(REFRESH_TOKEN_VALID_TIME));  // 쿠키 만료
    cookie.setHttpOnly(true);
    cookie.setPath("/");
    response.addCookie(cookie);
  }
}
