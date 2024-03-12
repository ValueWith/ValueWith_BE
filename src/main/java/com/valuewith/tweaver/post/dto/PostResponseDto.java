package com.valuewith.tweaver.post.dto;

import com.valuewith.tweaver.post.entity.Post;
import java.time.format.DateTimeFormatter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PostResponseDto {

  private Long postId;
  private String title;
  private String content;
  private Long postLikeNumber;
  private Long commentNumber;
  private String createAt;

  public static PostResponseDto from(Post post) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    return PostResponseDto.builder()
        .postId(post.getPostId())
        .title(post.getTitle())
        .content(post.getContent())
        .createAt(post.getCreatedDateTime().format(formatter))
        .build();
  }

  public static PostResponseDto from(Long postLikeNumber) {
    return PostResponseDto.builder()
        .postLikeNumber(postLikeNumber)
        .build();
  }
}
