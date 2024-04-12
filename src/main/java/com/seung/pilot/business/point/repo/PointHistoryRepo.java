package com.seung.pilot.business.point.repo;

import com.seung.pilot.business.point.domain.PointHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointHistoryRepo extends JpaRepository<PointHistory, Long> {
}
