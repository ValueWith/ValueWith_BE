package com.valuewith.tweaver.post.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PostUpdateForm {
  @NotBlank(message = "제목에 필요한 최소 글자 수를 맞춰주세요.")
  @Size(min = 2, message = "제목에 필요한 최소 글자 수를 맞춰주세요.")
  private String title;
  @NotBlank(message = "내용에 필요한 최소 글자 수를 맞춰주세요.")
  @Size(min = 2, message = "내용에 필요한 최소 글자 수를 맞춰주세요.")
  private String content;
}
