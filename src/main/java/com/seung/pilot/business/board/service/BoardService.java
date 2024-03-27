package com.seung.pilot.business.board.service;

import com.seung.pilot.business.board.domain.Board;
import com.seung.pilot.business.board.repo.BoardRepo;
import com.seung.pilot.commons.dto.request.board.BoardRequest;
import com.seung.pilot.commons.dto.request.board.UpdateBoardRequest;
import com.seung.pilot.commons.dto.response.board.BoardResponse;
import com.seung.pilot.commons.exception.NotAuthorizedException;
import com.seung.pilot.commons.utils.ModelMapperUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepo boardRepo;

    public BoardResponse insert(Long userId, BoardRequest boardRequest) {
        Board board = Board.insert(userId, boardRequest);
        boardRepo.save(board);
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
        board.updateBoard(updateBoardRequest);
    }

    public void delete(Long bdId, Long userId) {
        Board board = this.getOne(bdId);

        if(board.getUserId() != userId) {
            throw new NotAuthorizedException("삭제 불가능한 계정입니다.");
        }

        boardRepo.delete(board);
    }
}
