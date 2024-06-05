package com.seung.pilot.business.point.repo;

import com.seung.pilot.business.point.domain.PointHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PointHistoryRepo extends JpaRepository<PointHistory, Long> {
    List<PointHistory> findAllByUserIdOrderByCreatedDateDesc(Long userId);
}
