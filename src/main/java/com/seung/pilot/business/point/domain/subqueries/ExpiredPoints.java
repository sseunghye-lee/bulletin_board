package com.seung.pilot.business.point.domain.subqueries;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;
import org.hibernate.annotations.Synchronize;

import java.time.LocalDateTime;

@Entity
@Subselect(
        "select " +
                "       a.user_id " +
                "     , sum(a.amount) as expired_point " +
                "  from point_history_detail a " +
                " group by a.point_accumulate_id " +
                " , a.end_dt " +
                "having sum(a.amount) > 0 " +
                "   and a.end_dt < current_timestamp "
)
@Immutable
@Synchronize("PointHistoryDetail")
@Getter
public class ExpiredPoints {
    @Id
    private Long userId;
    private Long expiredPoint;
    private Long amount;
    private LocalDateTime endDt;
    private Long pointAccumulateId;
}
