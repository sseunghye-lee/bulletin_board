package com.seung.pilot.business.point.repo;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.seung.pilot.business.point.QPointHistory;
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
}
