package com.seung.pilot;

import com.seung.pilot.business.point.service.PointHistoryDetailService;
import com.seung.pilot.commons.dto.response.point.GetExpiredPointDto;
import com.seung.pilot.commons.dto.response.point.GetTotalExpiredPointDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
@ExtendWith(SpringExtension.class)
public class PointHistoryDetailServiceTest {

    @Autowired
    private PointHistoryDetailService pointHistoryDetailService;

    @Test
    void getTotalExpiredPoints() {
        List<GetTotalExpiredPointDto> list = pointHistoryDetailService.getTotalExpiredPointList();
        list.stream().forEach(System.out::println);
        list.stream().forEach(
                p -> {
                    List<GetExpiredPointDto> pointList = pointHistoryDetailService.getExpiredPointList(p.getUserId());
                    pointList.stream().forEach(System.out::println);
                }
        );
    }

    @Test
    @Rollback(value = false)
    void deadlinePoints() {
        pointHistoryDetailService.deadlinePoint();
    }
}
