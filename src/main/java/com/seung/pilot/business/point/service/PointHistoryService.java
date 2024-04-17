package com.seung.pilot.business.point.service;

import com.seung.pilot.business.point.repo.PointHistoryRepo;
import com.seung.pilot.business.point.repo.PointHistoryRepoSupport;
import com.seung.pilot.commons.dto.response.point.GetTotalPointDto;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PointHistoryService {
    private final PointHistoryRepoSupport pointHistoryRepoSupport;
    private final PointHistoryRepo pointHistoryRepo;

    public GetTotalPointDto getPointTotal(Long userId) {
        return pointHistoryRepoSupport.getTotalPoint(userId);
    }
}
