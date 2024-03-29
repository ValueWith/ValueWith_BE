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
  private Integer postLikeNumber;
  private Integer commentNumber;
  private String createAt;

  public static PostResponseDto from(Post post) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    int commentsNums = 0, postLikesNums = 0;
    if (post.getPostLikes() != null) {
      postLikesNums = post.getPostLikes().size();
    }
    if (post.getComments() != null) {
      commentsNums = post.getComments().size();
    }
    return PostResponseDto.builder()
        .postId(post.getPostId())
        .title(post.getTitle())
        .content(post.getContent())
        .commentNumber(commentsNums)
        .postLikeNumber(postLikesNums)
        .createAt(post.getCreatedDateTime().format(formatter))
        .build();
  }
}
