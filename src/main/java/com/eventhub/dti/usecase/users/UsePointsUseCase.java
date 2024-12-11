package com.eventhub.dti.usecase.users;

import java.math.BigDecimal;
import java.util.UUID;

public interface UsePointsUseCase {
    /**
     * Uses points to get discount on ticket price
     * @param userId The ID of the user
     * @param points Number of points to use
     * @param ticketPrice Original ticket price
     * @return Final price after discount
     */
    BigDecimal execute(UUID userId, Integer points, BigDecimal ticketPrice);
}
