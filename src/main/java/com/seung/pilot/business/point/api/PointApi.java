package com.seung.pilot.business.point.api;

import com.seung.pilot.business.point.service.PointHistoryService;
import com.seung.pilot.commons.dto.response.point.GetTotalPointDto;
import com.seung.pilot.commons.resolver.LoginUser;
import com.seung.pilot.commons.security.CustomUserDetails;
import com.seung.pilot.commons.utils.ApiUtils.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
