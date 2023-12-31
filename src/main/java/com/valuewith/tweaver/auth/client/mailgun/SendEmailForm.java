package com.valuewith.tweaver.auth.client.mailgun;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SendEmailForm {

  /**
   * mailgun에서 요구하는 필드
   * 발신자, 수신자, 제목, 내용
   */
  private String from;
  private String to;
  private String subject;
  private String text;
}
