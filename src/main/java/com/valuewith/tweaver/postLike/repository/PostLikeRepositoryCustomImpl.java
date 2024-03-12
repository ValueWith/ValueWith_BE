package com.valuewith.tweaver.postLike.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.valuewith.tweaver.postLike.entity.QPostLike;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PostLikeRepositoryCustomImpl implements PostLikeRepositoryCustom{

  private final JPAQueryFactory queryFactory;
  private final QPostLike qPostLike;

  public Long countPostLikeByPostId(Long postId) {
    return queryFactory
        .select(qPostLike.count())
        .from(qPostLike)
        .where(qPostLike.post.postId.eq(postId))
        .fetchOne();
  }
}
