package com.valuewith.tweaver.message.dto;

import com.valuewith.tweaver.auditing.BaseEntity;
import com.valuewith.tweaver.auth.dto.LoginMemberIdDto;
import com.valuewith.tweaver.member.entity.Member;
import com.valuewith.tweaver.message.entity.Message;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {
  private LoginMemberIdDto memberIdDto;
  private String content;
  private LocalDateTime created_at;

  public static MessageDto from(Message message) {
    Member member = message.getMember();
    return MessageDto.builder()
        .content(message.getContent())
        .memberIdDto(LoginMemberIdDto.from(member))
        .created_at(message.getCreatedDateTime())
        .build();
  }
}

