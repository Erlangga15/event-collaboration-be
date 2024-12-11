package com.eventhub.dti.usecase.users;

import com.eventhub.dti.infrastructure.dto.LoginResponseDTO;
import com.eventhub.dti.infrastructure.dto.UserRegisterRequestDTO;

/**
 * Registers a new user with the system and generates a referral code
 * 
 * @param request      Registration details including email, password, fullName,
 *                     phone, and role
 * @param referralCode Optional referral code to use during registration
 * @return Registered user with generated referral code
 */
public interface RegisterUserUseCase {
    LoginResponseDTO registerUser(UserRegisterRequestDTO request, String referralCode);
}
