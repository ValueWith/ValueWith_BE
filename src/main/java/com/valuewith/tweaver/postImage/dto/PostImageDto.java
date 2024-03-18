package com.valuewith.tweaver.postImage.dto;

import com.valuewith.tweaver.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostImageDto {

  private String postImageUrl;
  private Post post;

  public PostImageDto setPostImageUrlAndPost(String postImageUrl, Post post) {
    return PostImageDto.builder()
        .postImageUrl(postImageUrl)
        .post(post)
        .build();
  }
}
