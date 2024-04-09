package com.seung.pilot.commons.dto.response.board;

import com.seung.pilot.commons.enums.BoardCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GetMyBoardListResponse {
    private Long bdId;
    private String title;
    private Long view;
    private Long createdBy;
    private LocalDateTime createdDate;
    private BoardCategory boardCategory;
    private Long userId;
}
