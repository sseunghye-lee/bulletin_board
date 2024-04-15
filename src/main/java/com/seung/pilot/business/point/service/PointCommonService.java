package com.seung.pilot.business.point.service;

import com.seung.pilot.business.point.domain.PointHistory;
import com.seung.pilot.business.point.domain.PointHistoryDetail;
import com.seung.pilot.business.point.repo.PointHistoryDetailRepo;
import com.seung.pilot.business.point.repo.PointHistoryRepo;
import com.seung.pilot.commons.enums.PointHistoryType;
import com.seung.pilot.commons.enums.PointStat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class PointCommonService {

    private final PointHistoryRepo pointHistoryRepo;
    private final PointHistoryDetailRepo pointHistoryDetailRepo;

    public Long addPoint(Long userId) {
        PointHistory pointHistory = pointHistoryRepo.save(
                PointHistory.builder()
                        .userId(userId)
                        .amount(3L)
                        .pointHistoryType(PointHistoryType.REGISTRATION)
                        .pointStat(PointStat.ACCUMULATE)
                        .endDt(LocalDateTime.now().plusMonths(2L))
                        .remarks("게시글 등록 적립")
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
