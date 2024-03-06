package com.valuewith.tweaver.calendar.repository;

import static com.valuewith.tweaver.group.entity.QTripGroup.tripGroup;
import static com.valuewith.tweaver.post.entity.QPost.post;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
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

  private BooleanExpression eqType(String type) {
    return "mini".equals(type) ?
        JPAExpressions.selectOne()
            .from(post)
            .where(post.tripGroup.eq(tripGroup)).notExists() :
        null;
  }
}
