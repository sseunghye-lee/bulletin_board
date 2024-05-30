package com.seung.pilot.commons.dto.request.point;

import com.seung.pilot.commons.enums.PointHistoryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsePointRequest {
    private Long userId;
    private Long amount;
    private PointHistoryType pointHistoryType;
    private String remarks;
}
