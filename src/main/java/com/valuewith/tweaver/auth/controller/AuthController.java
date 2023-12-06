package com.valuewith.tweaver.auth.controller;

import com.valuewith.tweaver.auth.dto.AuthDto.EmailInput;
import com.valuewith.tweaver.auth.dto.AuthDto.SignUpForm;
import com.valuewith.tweaver.auth.dto.AuthDto.TokensAndMemberId;
import com.valuewith.tweaver.auth.dto.AuthDto.VerificationForm;
import com.valuewith.tweaver.auth.service.AuthService;
import com.valuewith.tweaver.config.SwaggerConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = {SwaggerConfig.AUTH_TAG})
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/auth")
public class AuthController {

  private final AuthService authService;

  @ApiOperation(
      value = "회원가입 요청",
      notes = "입력 정보\n"
          + "file: jpg, jpeg, png 파일만 올려주세요.\n"
          + "age: 나이 (숫자),\n"
          + "email: 이메일,\n"
          + "password:  비밀번호,\n"
          + "gender: male(남) / female(여),\n"
          + "nickname: 사이트 내에서 보여질 닉네임"
  )
  @ApiResponses(value = {
      @ApiResponse(code = 502, message = "프로필 이미지가 없습니다.")
  })
  @PostMapping(value = "/signup")
  public void signUp(@Valid SignUpForm request,
      @RequestPart(required = false) MultipartFile file) {
    authService.signUp(request, file);
  }

  @ApiOperation(
      value = "이메일로 인증번호 요청",
      notes = "이메일 인증번호 유효 시간은 5분입니다."
  )
  @ApiResponses(value = {
      @ApiResponse(code = 502, message = "이메일 전송에 실패하였습니다."),
      @ApiResponse(code = 409, message = "중복된 이메일입니다.")
  })
  @PostMapping("/verify")
  public void sendCode(@Valid @RequestBody EmailInput request) {
    authService.sendEmailVerification(request);
  }

  @ApiOperation(
      value = "이메일 인증 확인 요청",
      notes = "이메일로 보내진 인증번호를 확인합니다."
  )
  @ApiResponses(value = {
      @ApiResponse(code = 401, message = "인증코드에 문제가 있을 경우 발생합니다.")
  })
  @PostMapping("/verify/check")
  public ResponseEntity<Boolean> checkCode(@RequestBody VerificationForm request) {
    return ResponseEntity.ok(authService.isVerified(request));  // true
  }

  @ApiOperation(
      value = "리프레쉬 토큰으로 액세스 토큰 재발급 요청",
      notes = "액세스 토큰 만료시 리프레쉬 토큰으로 액세스 토큰을 재발급 합니다."
  )
  @ApiResponses(value = {
      @ApiResponse(code = 400, message = "빈 값으로 요청시 발생합니다.")
  })
  @PostMapping("/refresh")
  public ResponseEntity<TokensAndMemberId> reissueAccessToken(HttpServletResponse response,
      @RequestBody String refreshToken) {

    TokensAndMemberId memberData = authService.reissueTwoTokens(response, refreshToken);

    return ResponseEntity.ok(memberData);
  }
}


