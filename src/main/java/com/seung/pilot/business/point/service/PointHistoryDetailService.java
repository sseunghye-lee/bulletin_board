package com.seung.pilot.business.point.service;

import com.seung.pilot.business.point.domain.PointHistory;
import com.seung.pilot.business.point.domain.PointHistoryDetail;
import com.seung.pilot.business.point.repo.PointHistoryDetailRepo;
import com.seung.pilot.business.point.repo.PointHistoryDetailRepoSupport;
import com.seung.pilot.business.point.repo.PointHistoryRepo;
import com.seung.pilot.commons.dto.response.point.GetAvailablePointDto;
import com.seung.pilot.commons.dto.response.point.GetExpiredPointDto;
import com.seung.pilot.commons.dto.response.point.GetTotalExpiredPointDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PointHistoryDetailService {
    private final PointHistoryDetailRepoSupport pointHistoryDetailRepoSupport;
    private final PointHistoryRepo pointHistoryRepo;
    private final PointHistoryDetailRepo pointHistoryDetailRepo;

    public List<GetAvailablePointDto> getAvailablePointList(Long userId) {
        return pointHistoryDetailRepoSupport.getAvailablePoint(userId);
    }

    public List<GetExpiredPointDto> getExpiredPointList(Long userId) {
        return pointHistoryDetailRepoSupport.getExpiredPoint(userId);
    }

    public List<GetTotalExpiredPointDto> getTotalExpiredPointList() {
        return pointHistoryDetailRepoSupport.getTotalExpiredPoint();
    }

    public void deadlinePoint() {
        List<GetTotalExpiredPointDto> totalExpiredPoint = getTotalExpiredPointList();
        totalExpiredPoint.forEach(
                p -> {
                    PointHistory insertPointHistory = PointHistory.init(p);
                    pointHistoryRepo.save(insertPointHistory);

                    List<GetExpiredPointDto> expiredPoint = getExpiredPointList(p.getUserId());
                    expiredPoint.forEach(e -> {
                        PointHistoryDetail insertPointHistoryDetail = PointHistoryDetail.init(e,
                                insertPointHistory.getPointHistoryId());
                        pointHistoryDetailRepo.save(insertPointHistoryDetail);
                    });
                }
        );
    }

}
