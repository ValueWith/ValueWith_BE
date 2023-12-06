package com.valuewith.tweaver.auth.dto;

import com.valuewith.tweaver.member.entity.Member;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel("로그인 사용자 정보")
public class LoginMemberIdDto {
  private Long memberId;
  @ApiModelProperty(name = "사용자 닉네임", example = "김트위버")
  private String memberNickname;
  @ApiModelProperty(name = "사용자 이메일", example = "tweaver@tweaver.com")
  private String memberEmail;
  @ApiModelProperty(name = "사용자 프로필 사진", example = "http://s3_member_default.s3")
  private String memberProfileUrl;

  public static LoginMemberIdDto from(Member member) {
    return LoginMemberIdDto.builder()
        .memberId(member.getMemberId())
        .memberNickname(member.getNickName())
        .memberEmail(member.getEmail())
        .memberProfileUrl(member.getProfileUrl())
        .build();
  }
}
