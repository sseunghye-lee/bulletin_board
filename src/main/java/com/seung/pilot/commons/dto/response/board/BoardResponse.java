package com.seung.pilot.commons.dto.response.board;

import com.seung.pilot.commons.enums.BoardCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardResponse {
    private Long bdId;
    private BoardCategory boardCategory;
    private String title;
    private Long view;
    private String content;
    private Long userId;
}
