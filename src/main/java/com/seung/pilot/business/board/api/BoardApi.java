package com.seung.pilot.business.board.api;

import com.seung.pilot.business.board.service.BoardService;
import com.seung.pilot.commons.dto.request.board.BoardRequest;
import com.seung.pilot.commons.dto.request.board.UpdateBoardRequest;
import com.seung.pilot.commons.dto.response.board.BoardResponse;
import com.seung.pilot.commons.resolver.LoginUser;
import com.seung.pilot.commons.security.CustomUserDetails;
import com.seung.pilot.commons.utils.ApiUtils.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.seung.pilot.commons.utils.ApiUtils.success;

@RequiredArgsConstructor
@RestController
@RequestMapping("/board")
public class BoardApi {
    private final BoardService boardService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    public ApiResult<BoardResponse> add(@LoginUser CustomUserDetails customUserDetails, @RequestBody BoardRequest boardRequest) {
        return success(boardService.insert(customUserDetails.getUserId(), boardRequest));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PatchMapping("/{bdId}")
    public ApiResult<?> update(@PathVariable("bdId") Long bdId, @LoginUser CustomUserDetails customUserDetails, @RequestBody UpdateBoardRequest updateBoardRequest) {
        boardService.updateBoard(bdId, customUserDetails.getUserId(), updateBoardRequest);
        return success();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/{bdId}")
    public ApiResult<?> delete(@PathVariable("bdId") Long bdId, @LoginUser CustomUserDetails customUserDetails) {
        boardService.delete(bdId, customUserDetails.getUserId());
        return success();
    }

}
