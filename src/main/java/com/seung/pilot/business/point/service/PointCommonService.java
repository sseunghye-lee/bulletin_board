package com.seung.pilot.business.point.service;

import com.seung.pilot.business.point.domain.PointHistory;
import com.seung.pilot.business.point.domain.PointHistoryDetail;
import com.seung.pilot.business.point.repo.PointHistoryDetailRepo;
import com.seung.pilot.business.point.repo.PointHistoryRepo;
import com.seung.pilot.business.point.repo.PointHistoryRepoSupport;
import com.seung.pilot.commons.dto.request.point.UsePointRequest;
import com.seung.pilot.commons.dto.response.point.GetAvailablePointDto;
import com.seung.pilot.commons.dto.response.point.GetTotalPointDto;
import com.seung.pilot.commons.enums.PointHistoryType;
import com.seung.pilot.commons.enums.PointStat;
import com.seung.pilot.commons.exception.NotEnoughPointException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PointCommonService {

    private final PointHistoryRepo pointHistoryRepo;
    private final PointHistoryDetailRepo pointHistoryDetailRepo;
    private final PointHistoryRepoSupport pointHistoryRepoSupport;
    private final PointHistoryDetailService pointHistoryDetailService;

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

    public Long usePoint(UsePointRequest request) {
        long amount = request.getAmount();

        checkTotal(request.getUserId(), request.getAmount());

        PointHistory pointHistory = pointHistoryRepo.save(
                PointHistory.builder()
                        .userId(request.getUserId())
                        .amount(amount * -1)
                        .pointHistoryType(request.getPointHistoryType())
                        .pointStat(PointStat.USE)
                        .remarks(request.getRemarks())
                        .build()
        );

        List<GetAvailablePointDto> list = pointHistoryDetailService.getAvailablePointList(request.getUserId());

        List<PointHistoryDetail> historyDetailList = new ArrayList<>();

        for(GetAvailablePointDto dto : list) {
            long availableAmount = dto.getAvailableAmount();
            long minusAmount = amount >= availableAmount ? availableAmount : amount;

            historyDetailList.add(
                    PointHistoryDetail.builder()
                            .pointHistoryId(pointHistory.getPointHistoryId())
                            .amount(minusAmount * -1)
                            .userId(pointHistory.getUserId())
                            .endDt(dto.getEndDt())
                            .pointAccumulateId(dto.getPointAccumulateId())
                            .build()
            );

            if(amount <= availableAmount) break;

            amount -= minusAmount;
        }

        pointHistoryDetailRepo.saveAll(historyDetailList);

        return pointHistory.getPointHistoryId();

    }

    private void checkTotal(Long userId, Long amount) {
        GetTotalPointDto totalPointDto = pointHistoryRepoSupport.getTotalPoint(userId);

        if(totalPointDto.getTotalPoint() < amount || totalPointDto.getTotalPoint() == null) {
            throw new NotEnoughPointException("포인트가 부족합니다.");
        }
    }

}
