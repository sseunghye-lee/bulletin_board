package com.seung.pilot.business.point.scheduler;

import com.seung.pilot.business.point.service.PointHistoryDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PointHistorySchedulerService {
    private final PointHistoryDetailService pointHistoryDetailService;

    @Scheduled(cron = "0 0 24 * * *")
    public void deadlineTicket() {
        try {
            pointHistoryDetailService.deadlinePoint();
        } catch (RuntimeException exception) {

        }
    }
}
