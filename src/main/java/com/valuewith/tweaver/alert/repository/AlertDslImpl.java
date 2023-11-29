package com.valuewith.tweaver.alert.repository;

import static com.valuewith.tweaver.alert.entity.QAlert.*;
import static com.valuewith.tweaver.group.entity.QTripGroup.*;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.valuewith.tweaver.alert.dto.AlertResponseDto;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

@RequiredArgsConstructor
public class AlertDslImpl implements AlertDsl {

  private final JPAQueryFactory query;

  @Override
  public Slice<AlertResponseDto> getAlertsByMemberId(Long memberId, Pageable pageable) {
    List<AlertResponseDto> alertResponseDtoList = query.select(
            Projections.fields(AlertResponseDto.class,
                alert.alertId,
                alert.redirectUrl,
                alert.content,
                alert.isChecked,
                alert.createdDateTime,
                alert.groupId,
                alert.groupName)
        ).from(alert)
        .where((alert.member.memberId.eq(memberId))
            .and(alert.isDeleted.eq(false))
        )
        .orderBy(alert.createdDateTime.desc())
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize() + 1)
        .fetch();

    List<AlertResponseDto> alerts = new ArrayList<>();
    for (AlertResponseDto alertResponseDto: alertResponseDtoList) {
      alerts.add(alertResponseDto);
    }

    boolean hasNext = false;
    if (alerts.size() > pageable.getPageSize()) {
      alerts.remove(pageable.getPageSize());
      hasNext = true;
    }

    return new SliceImpl<>(alerts, pageable, hasNext);
  }

  @Override
  public Long getAlertCountByMemberId(Long memberId) {
    return query.select(alert.count())
        .from(alert)
        .where((alert.member.memberId.eq(memberId))
            .and(alert.isDeleted.eq(false))
            .and(alert.isChecked.eq(false))
        ).fetchOne();
  }

  @Override
  public void checkAllByMemberId(Long memberId) {
    query
        .update(alert)
        .set(alert.isChecked, true)
        .where(alert.member.memberId.eq(memberId))
        .execute();
  }

}
