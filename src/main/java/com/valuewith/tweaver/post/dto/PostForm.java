package com.valuewith.tweaver.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostForm {
  private Long tripGroupId;
  private String title;
  private String content;
}
