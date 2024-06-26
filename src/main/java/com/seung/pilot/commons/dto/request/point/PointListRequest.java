package com.seung.pilot.commons.dto.request.point;

import com.seung.pilot.commons.dto.request.commons.BasicGetListRequest;
import com.seung.pilot.commons.enums.PointHistoryType;
import com.seung.pilot.commons.enums.PointStat;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class PointListRequest extends BasicGetListRequest {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime endDt;
    private PointHistoryType pointHistoryType;
    private PointStat pointStat;
    private String search;
}
