package com.valuewith.tweaver.group.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.valuewith.tweaver.constants.GroupStatus;
import com.valuewith.tweaver.group.entity.QTripGroup;
import com.valuewith.tweaver.group.entity.TripGroup;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@RequiredArgsConstructor
public class TripGroupRepositoryCustomImpl implements TripGroupRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final QTripGroup qTripGroup = QTripGroup.tripGroup;

    @Override
    public List<TripGroup> findFilteredTripGroups(
        String status, String area, String title, Pageable pageable
    ) {
        BooleanBuilder predicate = createPredicateForTripGroup(status, area, title);
        return queryFactory
            .selectFrom(qTripGroup)
            .where(predicate)
            .leftJoin(qTripGroup.member).fetchJoin()
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .orderBy(sortOrder(pageable.getSort()).stream().toArray(OrderSpecifier[]::new))
            .fetch();
    }

    public long countFilteredTripGroups(String status, String area, String title) {
        BooleanBuilder predicate = createPredicateForTripGroup(status, area, title);
        return queryFactory
            .select(qTripGroup.count())
            .from(qTripGroup)
            .where(predicate)
            .fetchOne();
    }


    // 스프링의 Sort 객체를 QueryDSL OrderSpecifier로 변환
    private List<OrderSpecifier> sortOrder(Sort sort) {
        List<OrderSpecifier> orders = new ArrayList<>();
        //sort
        sort.stream().forEach(order -> {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String prop = order.getProperty();
            PathBuilder orderByExpression = new PathBuilder<>(TripGroup.class, "tripGroup");
            orders.add(new OrderSpecifier(direction, orderByExpression.get(prop)));
        });
        return orders;
    }

    private BooleanBuilder createPredicateForTripGroup(String status, String area, String title) {
        BooleanBuilder predicate = new BooleanBuilder();

        if (!"all".equalsIgnoreCase(status)) {
            predicate.and(qTripGroup.status.eq(GroupStatus.valueOf(status.toUpperCase())));
        }
        if (!"all".equalsIgnoreCase(area)) {
            predicate.and(qTripGroup.tripArea.eq(area));
        }
        if (title != null && !title.trim().isEmpty()) {
            predicate.and(qTripGroup.name.containsIgnoreCase(title.trim()));
        }

        return predicate;
    }
}