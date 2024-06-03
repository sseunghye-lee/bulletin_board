package com.seung.pilot.business.point.domain;

import com.seung.pilot.commons.BaseEntity;
import com.seung.pilot.commons.dto.response.point.GetTotalExpiredPointDto;
import com.seung.pilot.commons.enums.PointHistoryType;
import com.seung.pilot.commons.enums.PointStat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class PointHistory extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -7094631532990738540L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_history_id", columnDefinition = "bigint COMMENT '포인트 히스토리 아이디'")
    private Long pointHistoryId;

    @Column(name = "user_id", columnDefinition = "bigint COMMENT '사용자 아이디'")
    private Long userId;

    @Column(name = "amount", columnDefinition = "bigint COMMENT '포인트 점수'")
    private Long amount;

    @Enumerated(EnumType.STRING)
    @Column(name="history_type", columnDefinition = "varchar(50) COMMENT '타입(사용경로)'")
    private PointHistoryType pointHistoryType;

    @Enumerated(EnumType.STRING)
    @Column(name="point_stat", columnDefinition = "varchar(50) COMMENT '유형'")
    private PointStat pointStat;

    @Column(name="end_dt", columnDefinition = "datetime COMMENT '포인트 만료일'")
    private LocalDateTime endDt;

    @Column(name="remarks", columnDefinition = "text COMMENT '비고'")
    private String remarks;

    public PointHistory deadline() {
        this.pointStat = PointStat.EXPIRATION;
        return this;
    }

    public static PointHistory init(GetTotalExpiredPointDto totalExpiredPoint) {
        return PointHistory.builder().userId(totalExpiredPoint.getUserId())
                .amount(totalExpiredPoint.getTotalExpiredPoint() * -1)
                .pointHistoryType(null)
                .pointStat(PointStat.EXPIRATION)
                .endDt(null)
                .remarks("만료소멸")
                .build();
    }
}
