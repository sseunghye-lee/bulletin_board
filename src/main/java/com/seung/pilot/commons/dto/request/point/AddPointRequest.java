package com.seung.pilot.commons.dto.request.point;

import com.seung.pilot.commons.enums.PointHistoryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddPointRequest {
    private Long userId;
    private Long amount;
    private PointHistoryType pointHistoryType;
    private LocalDateTime endDt;
    private String remarks;
}
