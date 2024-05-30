package com.seung.pilot.business.board.domain;

import com.seung.pilot.commons.BaseEntity;
import com.seung.pilot.commons.dto.request.board.BoardRequest;
import com.seung.pilot.commons.dto.request.board.UpdateBoardRequest;
import com.seung.pilot.commons.dto.response.board.BoardResponse;
import com.seung.pilot.commons.enums.BoardCategory;
import com.seung.pilot.commons.utils.ModelMapperUtil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Board extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -653080974997225600L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bd_id", columnDefinition = "bigint COMMENT '게시판 아이디'")
    private Long bdId;

    @Column(columnDefinition = "varchar(50) COMMENT '카테고리'")
    @Enumerated(EnumType.STRING)
    private BoardCategory boardCategory;

    @Column(columnDefinition = "varchar(255) COMMENT '제목'")
    private String title;

    @Column(columnDefinition = "bigint COMMENT '조회수'")
    private Long view;

    @Column(columnDefinition = "mediumtext COMMENT '내용'")
    private String content;

    @Column(columnDefinition = "bigint COMMENT '사용자 아이디'")
    private Long userId;

    public static Board insert(Long userId, BoardRequest boardRequest) {
        return Board.builder()
                .boardCategory(boardRequest.getBoardCategory())
                .title(boardRequest.getTitle())
                .view(0L)
                .content(boardRequest.getContent())
                .userId(userId)
                .build();
    }

    public BoardResponse convertDto() {
        return ModelMapperUtil.get().map(this, BoardResponse.class);
    }

    public Boolean updateBoard(UpdateBoardRequest updateBoardRequest) {
        this.title = updateBoardRequest.getTitle();
        this.content = updateBoardRequest.getContent();

        return true;
    }

    public void addView() {
        this.view = this.view + 1;
    }

}
