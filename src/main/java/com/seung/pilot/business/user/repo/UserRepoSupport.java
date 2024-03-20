package com.seung.pilot.business.user.repo;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.seung.pilot.business.user.domain.QUsers;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepoSupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;
    private final QUsers qUsers = QUsers.users;

    public UserRepoSupport(JPAQueryFactory jpaQueryFactory) {
        super(JPAQueryFactory.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }
}
