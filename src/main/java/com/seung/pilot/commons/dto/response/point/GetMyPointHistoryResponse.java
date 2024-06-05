package com.seung.pilot.commons.dto.response.point;

import com.seung.pilot.commons.enums.PointHistoryType;
import com.seung.pilot.commons.enums.PointStat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetMyPointHistoryResponse {
    private Long pointHistoryId;
    private Long amount;
    private LocalDateTime createdDate;
    private LocalDateTime endDt;
    private PointHistoryType pointHistoryType;
    private PointStat pointStat;
    private String remarks;
}
