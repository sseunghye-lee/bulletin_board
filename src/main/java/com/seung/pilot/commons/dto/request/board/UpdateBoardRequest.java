package com.seung.pilot.commons.dto.request.board;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBoardRequest {
    public String title;
    private String content;
}
