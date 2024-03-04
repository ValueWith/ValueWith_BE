package com.valuewith.tweaver.postHashtag.entity;

import com.valuewith.tweaver.hashtag.entity.Hashtag;
import com.valuewith.tweaver.member.entity.Member;
import com.valuewith.tweaver.post.entity.Post;
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
@Table(name = "POST_HASHTAG")
@Getter
@ToString
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostHashtag {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long post_hashtag_id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "post_id")
  private Post post;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "hashtag_id")
  private Hashtag hashtag;
}
