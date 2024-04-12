package com.seung.pilot.business.point.service;

import com.seung.pilot.business.point.domain.PointHistory;
import com.seung.pilot.business.point.domain.PointHistoryDetail;
import com.seung.pilot.business.point.repo.PointHistoryDetailRepo;
import com.seung.pilot.business.point.repo.PointHistoryRepo;
import com.seung.pilot.commons.dto.request.point.AddPointRequest;
import com.seung.pilot.commons.enums.PointHistoryType;
import com.seung.pilot.commons.enums.PointStat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PointCommonService {

    private final PointHistoryRepo pointHistoryRepo;
    private final PointHistoryDetailRepo pointHistoryDetailRepo;

    public Long addPoint(AddPointRequest request) {
        PointHistory pointHistory = pointHistoryRepo.save(
                PointHistory.builder()
                        .userId(request.getUserId())
                        .amount(3L)
                        .pointHistoryType(PointHistoryType.REGISTRATION)
                        .pointStat(PointStat.ACCUMULATE)
                        .endDt(request.getEndDt())
                        .remarks(request.getRemarks())
                        .build()
        );

        PointHistoryDetail pointHistoryDetail = pointHistoryDetailRepo.save(
                PointHistoryDetail.builder()
                        .pointHistoryId(pointHistory.getPointHistoryId())
                        .amount(pointHistory.getAmount())
                        .userId(pointHistory.getUserId())
                        .endDt(pointHistory.getEndDt())
                        .build()
        );

        pointHistoryDetail.setPointAccumulateId();

        return pointHistory.getPointHistoryId();
    }

}
