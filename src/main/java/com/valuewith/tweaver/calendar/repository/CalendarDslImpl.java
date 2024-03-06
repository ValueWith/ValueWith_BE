package com.valuewith.tweaver.calendar.repository;

import static com.valuewith.tweaver.group.entity.QTripGroup.tripGroup;
import static com.valuewith.tweaver.post.entity.QPost.post;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.valuewith.tweaver.calendar.dto.CalendarDetailResponseDto;
import com.valuewith.tweaver.calendar.dto.CalendarResponseDto;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CalendarDslImpl implements CalendarDsl{
  private final JPAQueryFactory query;

  @Override
  public List<CalendarResponseDto> getCalenderList(Long memberId, LocalDate date, String type) {
    StringExpression formattedDate = Expressions.stringTemplate("FUNCTION('DATE_FORMAT', {0}, '%Y-%m')", tripGroup.tripDate);
    return query
        .select(
            Projections.fields(CalendarResponseDto.class,
                tripGroup.tripGroupId,
                tripGroup.tripDate,
                tripGroup.name)
        ).from(tripGroup)
        .where(formattedDate.eq(date.format(DateTimeFormatter.ofPattern("yyyy-MM"))),
            eqType(type))
        .fetch();
  }

  @Override
  public List<CalendarDetailResponseDto> getCalendarDetail(Long memberId, LocalDate date, Long tripGroupId) {
    StringExpression formattedDate = Expressions.stringTemplate("FUNCTION('DATE_FORMAT', {0}, '%Y-%m-%d')", post.tripGroup.tripDate);
    return query
        .select(
            Projections.fields(CalendarDetailResponseDto.class,
                post.postId,
                post.title,
                post.content)
        ).from(post)
        .where(post.member.memberId.eq(memberId)
            .and(post.tripGroup.tripGroupId.eq(tripGroupId))
            .and(formattedDate.eq(date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))))
        .fetch();
  }

  private BooleanExpression eqType(String type) {
    return "mini".equals(type) ?
        JPAExpressions.selectOne()
            .from(post)
            .where(post.tripGroup.eq(tripGroup)).notExists() :
        null;
  }
}
