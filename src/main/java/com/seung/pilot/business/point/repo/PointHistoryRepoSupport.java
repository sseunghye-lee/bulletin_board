package com.seung.pilot.business.point.repo;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.seung.pilot.business.point.domain.QPointHistory;
import com.seung.pilot.commons.dto.request.point.PointListRequest;
import com.seung.pilot.commons.dto.response.point.GetPointListResponse;
import com.seung.pilot.commons.dto.response.point.GetTotalPointDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

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

    public JPAQuery<?> getPointListQuery(JPAQuery<?> query, PointListRequest request,
                                         List<Long> userIds) {
        String search = request.getSearch();
        if (StringUtils.hasText(search)) {
            if (!userIds.isEmpty()) {
                query = query.where(
                        qPointHistory.userId.in(userIds)
                );
            }
        }

        if (request.getStartDate() != null && request.getEndDate() != null) {
            query = query.where(
                    qPointHistory.endDt.between(request.getStartDate(), request.getEndDate())
            );
        }

        if (request.getPointHistoryType() != null) {
            query = query.where(
                    qPointHistory.pointHistoryType.eq(request.getPointHistoryType())
            );
        }

        if (request.getPointStat() != null) {
            query = query.where(
                    qPointHistory.pointStat.eq(request.getPointStat())
            );
        }

        return query;
    }


    public Page<GetPointListResponse> getPointList(PointListRequest request, Pageable pageable, List<Long> userId) {
        JPAQuery<GetPointListResponse> query = jpaQueryFactory
                .select(Projections.bean(GetPointListResponse.class, qPointHistory.pointHistoryId, qPointHistory.amount,
                        qPointHistory.createdDate, qPointHistory.endDt, qPointHistory.pointHistoryType, qPointHistory.pointStat,
                        qPointHistory.remarks, qPointHistory.userId))
                .from(qPointHistory)
                .orderBy(qPointHistory.pointHistoryId.desc());

        if(pageable != null) {
            query = query.offset(pageable.getOffset())
                    .limit(pageable.getPageSize());
        }

        query = (JPAQuery<GetPointListResponse>) getPointListQuery(query, request, userId);

        List<GetPointListResponse> results = query.fetch();
        Long total = getPointListCount(request, userId);

        pageable = pageable == null ? PageRequest.of(0, 10) : pageable;

        return new PageImpl<>(results, pageable, total);
    }

    public Long getPointListCount(PointListRequest request, List<Long> userIds) {
        JPAQuery<Long> query = jpaQueryFactory.select(qPointHistory.count())
                .from(qPointHistory);
        query = (JPAQuery<Long>) getPointListQuery(query, request, userIds);

        return query.fetchOne();
    }
}
