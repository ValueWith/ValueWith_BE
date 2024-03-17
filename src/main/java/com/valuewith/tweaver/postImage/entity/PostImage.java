package com.valuewith.tweaver.postImage.entity;

import com.valuewith.tweaver.post.entity.Post;
import com.valuewith.tweaver.postImage.dto.PostImageDto;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "POST_IMAGE")
@Getter
@ToString
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostImage {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long postImageId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "post_id")
  private Post post;

  private String postImageUrl;

  public static PostImage from(PostImageDto postImageDto) {
    return PostImage.builder()
        .post(postImageDto.getPost())
        .postImageUrl(postImageDto.getPostImageUrl())
        .build();
  }
}
