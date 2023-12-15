package com.valuewith.tweaver.message.dto;

import com.valuewith.tweaver.auth.dto.LoginMemberIdDto;
import com.valuewith.tweaver.member.entity.Member;
import com.valuewith.tweaver.message.entity.Message;
import io.swagger.annotations.ApiModel;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("채팅 메시지와 사용자 정보")
public class MessageResponseDto {
  private Long messageId;
  private Long memberId;
  private String nickName;
  private String email;
  private String profileUrl;
  private String content;
  private LocalDateTime createdAt;
}

