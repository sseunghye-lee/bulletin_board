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
public class GetAvailablePointDto {
    private Long availableAmount;
    private LocalDateTime endDt;
    private Long pointAccumulateId;
}
