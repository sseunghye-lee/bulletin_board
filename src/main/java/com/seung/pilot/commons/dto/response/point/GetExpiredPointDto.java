package com.seung.pilot.commons.dto.response.point;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetExpiredPointDto {
    private Long totalExpiredPoint;
    private Long pointAccumulateId;
    private Long userId;
    private LocalDateTime endDt;
}
