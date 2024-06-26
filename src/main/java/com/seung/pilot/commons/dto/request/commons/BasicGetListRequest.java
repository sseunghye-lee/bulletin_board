package com.seung.pilot.commons.dto.request.commons;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BasicGetListRequest {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String search;
}
