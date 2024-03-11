package com.valuewith.tweaver.postImage.dto;

import com.valuewith.tweaver.post.dto.PostForm;
import com.valuewith.tweaver.post.entity.Post;
import com.valuewith.tweaver.postImage.entity.PostImage;
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
  private PostForm post;

  public PostImage setPostImageUrlAndPost(String postImageUrl, Post post) {
    return PostImage.builder()
        .postImageUrl(postImageUrl)
        .post(post)
        .build();
  }
}
