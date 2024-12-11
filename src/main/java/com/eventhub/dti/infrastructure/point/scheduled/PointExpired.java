package com.eventhub.dti.infrastructure.point.scheduled;

import com.eventhub.dti.infrastructure.point.service.PointService;
import org.springframework.scheduling.annotation.Scheduled;

public class PointExpired {
  private final PointService pointService;

  public PointExpired(PointService pointService) {
    this.pointService = pointService;
  }

  @Scheduled(cron = "0 0 24 * * *")
  public void checkAndUpdateExpiredPoints() {
    pointService.checkAndUpdateExpiredPoints();
  }
}
