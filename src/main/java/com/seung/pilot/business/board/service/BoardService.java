package com.seung.pilot.business.board.service;

import com.seung.pilot.business.board.domain.Board;
import com.seung.pilot.business.board.repo.BoardRepo;
import com.seung.pilot.business.board.repo.BoardRepoSupport;
import com.seung.pilot.business.point.service.PointCommonService;
import com.seung.pilot.commons.dto.request.board.BoardRequest;
import com.seung.pilot.commons.dto.request.board.UpdateBoardRequest;
import com.seung.pilot.commons.dto.request.commons.BasicGetListRequest;
import com.seung.pilot.commons.dto.request.point.UsePointRequest;
import com.seung.pilot.commons.dto.response.board.BoardResponse;
import com.seung.pilot.commons.dto.response.board.GetBoardListResponse;
import com.seung.pilot.commons.dto.response.board.GetMyBoardListResponse;
import com.seung.pilot.commons.enums.PointHistoryType;
import com.seung.pilot.commons.exception.NotAuthorizedException;
import com.seung.pilot.commons.utils.ModelMapperUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepo boardRepo;
    private final BoardRepoSupport boardRepoSupport;
    private final PointCommonService pointCommonService;

    public BoardResponse insert(Long userId, BoardRequest boardRequest) {
        Board board = Board.insert(userId, boardRequest);
        boardRepo.save(board);
        pointCommonService.addPoint(userId);
        return board.convertDto();
    }

    public Board getOne(Long bdId) {
        return boardRepo.findById(bdId).orElseThrow(() -> new EntityNotFoundException("not exists " + bdId));
    }

    public BoardResponse findBoard(Long bdId) {
        Board board = this.getOne(bdId);
        return ModelMapperUtil.get().map(board, BoardResponse.class);
    }

    public void updateBoard(Long bdId, Long userId, UpdateBoardRequest updateBoardRequest) {
        Board board = this.getOne(bdId);

        if(board.getUserId() != userId) {
            throw new NotAuthorizedException("수정 불가능한 계정입니다.");
        }

        Boolean flag = board.updateBoard(updateBoardRequest);

        if(flag) {
            UsePointRequest usePointRequest = new UsePointRequest(userId, 3L, PointHistoryType.MODIFY, "게시글 수정");
            pointCommonService.usePoint(usePointRequest);
        }

    }

    public void delete(Long bdId, Long userId) {
        Board board = this.getOne(bdId);

        if(board.getUserId() != userId) {
            throw new NotAuthorizedException("삭제 불가능한 계정입니다.");
        }

        boardRepo.delete(board);

        UsePointRequest usePointRequest = new UsePointRequest(userId, 2L, PointHistoryType.DELETE, "게시글 삭제");
        pointCommonService.usePoint(usePointRequest);
    }

    public void addView(long bdId) {
        Board board = this.getOne(bdId);
        board.addView();
    }

    public Page<GetBoardListResponse> getBoardList(BasicGetListRequest request, Pageable pageable) {
        List<GetBoardListResponse> list = boardRepoSupport.getBoardList(request, pageable);
        Long total = boardRepoSupport.getBoardCount(request);

        pageable = pageable == null ? PageRequest.of(0, 10) : pageable;

        return new PageImpl<>(list, pageable, total);
    }

    public Page<GetMyBoardListResponse> getMyBoardList(Long userId, BasicGetListRequest request, Pageable pageable) {
        List<GetMyBoardListResponse> list = boardRepoSupport.getMyBoardList(userId, request, pageable);
        Long total = boardRepoSupport.getMyBoardCount(request);

        pageable = pageable == null ? PageRequest.of(0, 10) : pageable;

        return new PageImpl<>(list, pageable, total);
    }
}
