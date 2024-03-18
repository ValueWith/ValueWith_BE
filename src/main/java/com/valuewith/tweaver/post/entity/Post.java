package com.valuewith.tweaver.post.entity;

import com.valuewith.tweaver.auditing.BaseEntity;
import com.valuewith.tweaver.comment.entity.Comment;
import com.valuewith.tweaver.group.entity.TripGroup;
import com.valuewith.tweaver.member.entity.Member;
import com.valuewith.tweaver.post.dto.PostUpdateForm;
import com.valuewith.tweaver.postImage.entity.PostImage;
import com.valuewith.tweaver.postLike.entity.PostLike;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "POST")
@Getter
@ToString
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE post SET IS_DELETED = 1 WHERE POST_ID = ?")
@Where(clause = "IS_DELETED = 0")
public class Post extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long postId;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "trip_group_id")
  private TripGroup tripGroup;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  private String title;

  private String content;

  @OneToMany(mappedBy = "post")
  private List<PostLike> postLikes;

  @OneToMany(mappedBy = "post")
  private List<Comment> comments;

  @OneToMany(mappedBy = "post")
  private List<PostImage> postImages;

  public void updateFrom(PostUpdateForm postUpdateForm) {
    this.title = postUpdateForm.getTitle();
    this.content = postUpdateForm.getContent();
  }
}
