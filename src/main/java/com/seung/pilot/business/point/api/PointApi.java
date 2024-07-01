package com.seung.pilot.business.point.api;

import com.seung.pilot.business.point.service.PointHistoryService;
import com.seung.pilot.commons.dto.request.point.PointListRequest;
import com.seung.pilot.commons.dto.response.point.GetMyPointHistoryResponse;
import com.seung.pilot.commons.dto.response.point.GetPointListResponse;
import com.seung.pilot.commons.dto.response.point.GetTotalPointDto;
import com.seung.pilot.commons.resolver.LoginUser;
import com.seung.pilot.commons.security.CustomUserDetails;
import com.seung.pilot.commons.utils.ApiUtils.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.seung.pilot.commons.utils.ApiUtils.success;

@RequiredArgsConstructor
@RestController
@RequestMapping("/point")
public class PointApi {
    private final PointHistoryService pointHistoryService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/total")
    public ApiResult<GetTotalPointDto> getTotalPoint(@LoginUser CustomUserDetails customUserDetails) {
        return success(pointHistoryService.getPointTotal(customUserDetails.getUserId()));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/my")
    public ApiResult<List<GetMyPointHistoryResponse>> getMyPointHistory(@LoginUser CustomUserDetails customUserDetails) {
        return success(pointHistoryService.getMyPointList(customUserDetails.getUserId()));
    }

    @PreAuthorize("hasRole('ROLE_MASTER')")
    @GetMapping
    public ApiResult<Page<GetPointListResponse>> getPointList(PointListRequest request, Pageable pageable) {
        return success(pointHistoryService.getPointList(request, pageable));
    }
}
