package com.seung.pilot.business.point.service;

import com.seung.pilot.business.point.domain.PointHistory;
import com.seung.pilot.business.point.repo.PointHistoryRepo;
import com.seung.pilot.business.point.repo.PointHistoryRepoSupport;
import com.seung.pilot.commons.dto.response.point.GetMyPointHistoryResponse;
import com.seung.pilot.commons.dto.response.point.GetTotalPointDto;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PointHistoryService {
    private final PointHistoryRepoSupport pointHistoryRepoSupport;
    private final PointHistoryRepo pointHistoryRepo;

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
}
