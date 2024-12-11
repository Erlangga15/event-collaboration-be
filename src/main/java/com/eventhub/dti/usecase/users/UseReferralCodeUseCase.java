package com.eventhub.dti.usecase.users;

import java.util.UUID;

public interface UseReferralCodeUseCase {
    /**
     * Records the usage of a referral code and awards points to the referrer
     * @param referralCode The referral code being used
     * @param userId The ID of the user using the referral code
     * @return True if referral code was successfully used, false otherwise
     */
    boolean execute(String referralCode, UUID userId);
}
