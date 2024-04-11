package com.seung.pilot.business.user.repo;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.seung.pilot.business.user.domain.QUsers;
import com.seung.pilot.commons.dto.request.commons.BasicGetListRequest;
import com.seung.pilot.commons.dto.response.user.GetUserListResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
public class UserRepoSupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;
    private final QUsers qUser = QUsers.users;

    public UserRepoSupport(JPAQueryFactory jpaQueryFactory) {
        super(JPAQueryFactory.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public JPAQuery<?> getUserListQuery(JPAQuery<?> query, BasicGetListRequest request) {
        String search = request.getSearch();

        if(StringUtils.hasText(search)) {
            query = query.where(qUser.userName.contains(search));
        }
        if (request.getStartDate() != null && request.getEndDate() != null) {
            query = query.where(
                    qUser.createdDate.between(request.getStartDate(), request.getEndDate())
            );
        }
        return query;
    }

    public Long getUserCount(BasicGetListRequest request) {
        JPAQuery<Long> query = jpaQueryFactory.select(qUser.count())
                .from(qUser);
        query = (JPAQuery<Long>) getUserListQuery(query, request);

        return query.fetchOne();
    }

    public List<GetUserListResponse> getUserList(BasicGetListRequest request, Pageable pageable) {
        JPAQuery<GetUserListResponse> query = jpaQueryFactory
                .select(Projections.bean(GetUserListResponse.class, qUser.userId, qUser.createdDate, qUser.gender,
                        qUser.nickName, qUser.userName))
                .from(qUser)
                .orderBy(qUser.createdDate.desc());

        if(pageable != null) {
            query = query.offset(pageable.getOffset())
                    .limit(pageable.getPageSize());
        }

        query = (JPAQuery<GetUserListResponse>) getUserListQuery(query, request);

        List<GetUserListResponse> results = query.fetch();

        return results;
    }
}
