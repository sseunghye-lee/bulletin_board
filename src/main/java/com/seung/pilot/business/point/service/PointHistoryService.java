package com.seung.pilot.business.point.service;

import com.seung.pilot.business.point.domain.PointHistory;
import com.seung.pilot.business.point.repo.PointHistoryRepo;
import com.seung.pilot.business.point.repo.PointHistoryRepoSupport;
import com.seung.pilot.business.user.service.UserService;
import com.seung.pilot.commons.dto.request.point.PointListRequest;
import com.seung.pilot.commons.dto.response.point.GetMyPointHistoryResponse;
import com.seung.pilot.commons.dto.response.point.GetPointListResponse;
import com.seung.pilot.commons.dto.response.point.GetTotalPointDto;
import com.seung.pilot.commons.dto.response.user.GetUserListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PointHistoryService {
    private final PointHistoryRepoSupport pointHistoryRepoSupport;
    private final PointHistoryRepo pointHistoryRepo;
    private final UserService userService;

    public GetTotalPointDto getPointTotal(Long userId) {
        return pointHistoryRepoSupport.getTotalPoint(userId);
    }

    public List<GetMyPointHistoryResponse> getMyPointList(Long userId) {
        List<PointHistory> list = pointHistoryRepo.findAllByUserIdOrderByCreatedDateDesc(userId);

        return list.stream().map(o -> GetMyPointHistoryResponse.builder()
                .pointHistoryId(o.getPointHistoryId())
                .pointHistoryType(o.getPointHistoryType())
                .pointStat(o.getPointStat())
                .amount(o.getAmount())
                .endDt(o.getEndDt())
                .remarks(o.getRemarks())
                .createdDate(o.getCreatedDate())
                .build()
        ).collect(Collectors.toList());
    }

    public Page<GetPointListResponse> getPointList(PointListRequest request, Pageable pageable) {
        String search = request.getSearch();
        List<Long> userIds = new ArrayList<>();

        if(search != null) {
            userIds = userService.findByUserName(search).stream().map(GetUserListResponse::getUserId)
                    .collect(Collectors.toList());
        }

        Page<GetPointListResponse> getPointList = pointHistoryRepoSupport.getPointList(request, pageable, userIds);

        this.inputUserName(getPointList);

        return getPointList;

    }

    private void inputUserName(Page<GetPointListResponse> getPointListResponse) {
        Set<Long> userId = getPointListResponse.getContent().stream()
                .map(GetPointListResponse::getUserId).collect(Collectors.toSet());

        Map<Long, GetUserListResponse> userListDtoMap = userService.getUsers(userId);

        getPointListResponse.getContent().stream().forEach(
                point -> {
                    GetUserListResponse userListDto = userListDtoMap.get(point.getUserId());
                    if(userListDto != null){
                        point.setNickName(userListDto.getNickName());
                    }
                }
        );

        getPointListResponse.getContent().stream().forEach(
                point -> {
                    GetUserListResponse userListDto = userListDtoMap.get(point.getUserId());
                    if(userListDto != null){
                        point.setUserName(userListDto.getUserName());
                    }
                }
        );
    }
}
