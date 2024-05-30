package com.seung.pilot.business.point.repo;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.seung.pilot.business.point.domain.QPointHistoryDetail;
import com.seung.pilot.commons.dto.response.point.GetAvailablePointDto;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PointHistoryDetailRepoSupport extends QuerydslRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;
    private final QPointHistoryDetail POINT_HISTORY_DETAIL = QPointHistoryDetail.pointHistoryDetail;
    public PointHistoryDetailRepoSupport(JPAQueryFactory jpaQueryFactory) {
        super(JPAQueryFactory.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    // 가용포인트 계산
    public List<GetAvailablePointDto> getAvailablePoint(Long userId) {
        List<GetAvailablePointDto> pointList = jpaQueryFactory
                .select(Projections.bean(
                        GetAvailablePointDto.class,
                        POINT_HISTORY_DETAIL.pointAccumulateId,
                        POINT_HISTORY_DETAIL.endDt,
                        POINT_HISTORY_DETAIL.amount.sum().as("availableAmount"))
                )
                .from(POINT_HISTORY_DETAIL)
                .where(POINT_HISTORY_DETAIL.userId.eq(userId))
                .groupBy(
                        POINT_HISTORY_DETAIL.pointAccumulateId,
                        POINT_HISTORY_DETAIL.endDt
                )
                .having(POINT_HISTORY_DETAIL.amount.sum().gt(0))
                .orderBy(POINT_HISTORY_DETAIL.endDt.asc())
                .fetch();

        return pointList;

    }

}
