package com.seung.pilot.business.point.repo;

import com.seung.pilot.business.point.domain.PointHistoryDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointHistoryDetailRepo extends JpaRepository<PointHistoryDetail, Long> {
}
