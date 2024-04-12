package com.seung.pilot.business.point.repo;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.seung.pilot.business.point.QPointHistoryDetail;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public class PointHistoryDetailRepoSupport extends QuerydslRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;
    private final QPointHistoryDetail POINT_HISTORY_DETAIL = QPointHistoryDetail.pointHistoryDetail;

    public PointHistoryDetailRepoSupport(JPAQueryFactory jpaQueryFactory) {
        super(JPAQueryFactory.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

}
