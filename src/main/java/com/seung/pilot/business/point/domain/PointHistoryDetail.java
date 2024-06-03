package com.seung.pilot.business.point.domain;

import com.seung.pilot.commons.BaseEntity;
import com.seung.pilot.commons.dto.response.point.GetExpiredPointDto;
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
public class PointHistoryDetail extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1195172057493790077L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "bigint not null comment '포인트 내역 상세 아이디'")
    private Long pointHistoryDetailId;

    @Column(columnDefinition = "bigint COMMENT '사용자 아이디'")
    private Long userId;

    @Column(columnDefinition = "bigint COMMENT '이용된 포인트'")
    private Long amount;

    @Column(columnDefinition = "bigint COMMENT '포인트 적립 아이디(포인트 내역 상세 아이디)'")
    private Long pointAccumulateId;

    @Column(columnDefinition = "datetime COMMENT '포인트 만료일'")
    private LocalDateTime endDt;

    @Column(columnDefinition = "bigint COMMENT '포인트 내역 아이디'")
    private Long pointHistoryId;

    public void setPointAccumulateId() {
        pointAccumulateId = pointHistoryDetailId;
    }

    public static PointHistoryDetail init(GetExpiredPointDto expiredPoint, Long historyId) {
        return PointHistoryDetail.builder().userId(expiredPoint.getUserId())
                .amount(expiredPoint.getTotalExpiredPoint() * -1)
                .pointAccumulateId(expiredPoint.getPointAccumulateId())
                .endDt(expiredPoint.getEndDt())
                .pointHistoryId(historyId)
                .build();
    }
}
