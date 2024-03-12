package com.valuewith.tweaver.post.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.valuewith.tweaver.group.entity.QTripGroup;
import com.valuewith.tweaver.post.entity.Post;
import com.valuewith.tweaver.post.entity.QPost;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class PostRepositoryCustomImpl implements PostRepositoryCustom {

  private final JPAQueryFactory queryFactory;
  private final QPost qPost = QPost.post;
  private final QTripGroup qTripGroup = QTripGroup.tripGroup;

  @Override
  public List<Post> findPostByTitleAndTripGroupArea(String title, String area, Pageable pageable) {
    BooleanBuilder predicate = createConditionWithTitleAndArea(title, area);
    return queryFactory
        .selectFrom(qPost)
        .leftJoin(qPost.tripGroup, qTripGroup).on(predicate).fetchJoin()
        .orderBy(qPost.createdDateTime.desc())
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();
  }

  private BooleanBuilder createConditionWithTitleAndArea(String title, String area) {
    BooleanBuilder builder = new BooleanBuilder();
    if (title != null && !title.trim().isEmpty()) {
      builder.and(qPost.title.eq(title));
    }
    if (!"all".equalsIgnoreCase(area)) {
      builder.and(qTripGroup.tripArea.eq(area));
    }
    return builder;
  }
}
