package com.seung.pilot.business.point.repo;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.seung.pilot.business.point.domain.QPointHistory;
import com.seung.pilot.commons.dto.response.point.GetTotalPointDto;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public class PointHistoryRepoSupport extends QuerydslRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;
    private final QPointHistory qPointHistory = QPointHistory.pointHistory;

    public PointHistoryRepoSupport(JPAQueryFactory jpaQueryFactory) {
        super(JPAQueryFactory.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public GetTotalPointDto getTotalPoint(Long userId) {
        JPAQuery<GetTotalPointDto> query = jpaQueryFactory
                .select(Projections.bean(GetTotalPointDto.class,
                        qPointHistory.amount.sum().as("totalPoint"), qPointHistory.userId))
                .from(qPointHistory)
                .where(qPointHistory.userId.eq(userId));
        return query.fetchOne();
    }
}
