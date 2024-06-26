package com.seung.pilot.commons.dto.request.point;

import com.seung.pilot.commons.enums.PointHistoryType;
import com.seung.pilot.commons.enums.PointStat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PointListRequest {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime endDt;
    private PointHistoryType pointHistoryType;
    private PointStat pointStat;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime endDate;
    private String search;
}
