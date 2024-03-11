package com.valuewith.tweaver.post.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.valuewith.tweaver.post.entity.Post;
import com.valuewith.tweaver.post.entity.QPost;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PostRepositoryCustomImpl implements PostRepositoryCustom {

  private final JPAQueryFactory queryFactory;
  private final QPost qPost = QPost.post;

  public List<Post> findPostsByTitle(String title) {
    return queryFactory.selectFrom(qPost)
        .where(qPost.title.eq(title))
        .fetch();
  }

}
