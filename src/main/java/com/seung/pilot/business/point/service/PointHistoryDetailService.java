package com.seung.pilot.business.point.service;

import com.seung.pilot.business.point.repo.PointHistoryDetailRepoSupport;
import com.seung.pilot.commons.dto.response.point.GetAvailablePointDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PointHistoryDetailService {
    private final PointHistoryDetailRepoSupport pointHistoryDetailRepoSupport;

    public List<GetAvailablePointDto> getAvailablePointList(Long userId) {
        return pointHistoryDetailRepoSupport.getAvailablePoint(userId);
    }
}
