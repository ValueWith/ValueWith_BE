package com.valuewith.tweaver.auth.dto;

import com.valuewith.tweaver.constants.Provider;
import com.valuewith.tweaver.member.entity.Member;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class AuthDto {

  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  public static class SignInForm {

    @Email
    private String email;
    private String password;
  }

  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  @Setter
  @ApiModel(
      value = "회원가입시 입력받아야 할 정보입니다.",
      description = "닉네임, 이메일, 비밀번호, 성별, 나이를 입력받습니다.\n"
      + "프로필 사진의 경우 jpg, png, jpeg 파일을 받습니다."
  )
  public static class SignUpForm {

    @ApiModelProperty(name = "닉네임", example = "김트위버")
    private String nickname;
    @ApiModelProperty(name = "이메일", example = "tweaver@tweaver.com")
    @Email
    private String email;
    @ApiModelProperty(name = "비밀번호", example = "123!@#tweaver")
    private String password;
    @ApiModelProperty(name = "성별", example = "male / female")
    private String gender;
    @ApiModelProperty(name = "나이", example = "20")
    private Integer age;

    public Member setProfileUrl(String profileUrl) {
      return Member.builder()
          .nickName(this.nickname)
          .email(this.email)
          .password(this.password)
          .gender(this.gender)
          .age(this.age)
          .provider(Provider.NORMAL)
          .profileUrl(profileUrl)
          .build();
    }
  }

  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  @ApiModel("이메일과 인증번호")
  public static class VerificationForm {

    @ApiModelProperty(name = "이메일", example = "tweaver@tweaver.com")
    @Email
    private String email;
    @ApiModelProperty(name = "인증코드", example = "123Aab")
    private String code;
  }

  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  @ApiModel("이메일 정보")
  public static class EmailInput {

    @ApiModelProperty(value = "이메일", example = "tweaver@tweaver.com")
    @Email
    private String email;
  }

  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  @ApiModel("엑세스 토큰과 리프레쉬 토큰, 로그인 사용자 정보")
  public static class TokensAndMemberId {
    @ApiModelProperty(value = "엑세스 토큰입니다.", example = "eyaaaa.bbbb.cccc")
    private String accessToken;
    @ApiModelProperty(value = "리프레쉬 토큰입니다.", example = "eyaaaa.bbbb.cccc")
    private String refreshToken;
    private LoginMemberIdDto loginMemberIdDto;

    public static TokensAndMemberId from(String accessToken, String refreshToken, LoginMemberIdDto idDto) {
      return TokensAndMemberId.builder()
          .accessToken(accessToken)
          .refreshToken(refreshToken)
          .loginMemberIdDto(idDto)
          .build();
    }
  }

  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  @ApiModel("리프레시 토큰")
  public static class RefreshToken {

    @ApiModelProperty(value = "리프레시 토큰", example = "abcd...")
    private String refreshToken;
  }
}
