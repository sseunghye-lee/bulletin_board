package com.seung.pilot.commons.dto.response.point;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetTotalExpiredPointDto {
    private Long totalExpiredPoint;
    private Long userId;
}
