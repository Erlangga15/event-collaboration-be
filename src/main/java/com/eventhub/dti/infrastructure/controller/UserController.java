package com.eventhub.dti.infrastructure.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eventhub.dti.common.response.Response;
import com.eventhub.dti.entity.User;
import com.eventhub.dti.infrastructure.dto.LoginResponseDTO;
import com.eventhub.dti.infrastructure.dto.TransactionResponseDTO;
import com.eventhub.dti.infrastructure.dto.UsePointsRequestDTO;
import com.eventhub.dti.infrastructure.dto.UsePointsResponseDTO;
import com.eventhub.dti.infrastructure.dto.UserDetailsResponseDTO;
import com.eventhub.dti.infrastructure.dto.UserPointsResponseDTO;
import com.eventhub.dti.infrastructure.dto.UserRegisterRequestDTO;
import com.eventhub.dti.infrastructure.dto.UserResponseDTO;
import com.eventhub.dti.infrastructure.dto.UserTicketResponseDTO;
import com.eventhub.dti.infrastructure.dto.UserUpdateRequestDTO;
import com.eventhub.dti.usecase.users.GetUserDetailsUseCase;
import com.eventhub.dti.usecase.users.GetUserPointsUseCase;
import com.eventhub.dti.usecase.users.GetUserTicketsUseCase;
import com.eventhub.dti.usecase.users.GetUserTransactionsUseCase;
import com.eventhub.dti.usecase.users.RegisterUserUseCase;
import com.eventhub.dti.usecase.users.UpdateUserProfileUseCase;
import com.eventhub.dti.usecase.users.UsePointsUseCase;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final RegisterUserUseCase registerUserUseCase;
    private final UpdateUserProfileUseCase updateUserProfileUseCase;
    private final GetUserPointsUseCase getUserPointsUseCase;
    private final UsePointsUseCase usePointsUseCase;
    private final GetUserDetailsUseCase getUserDetailsUseCase;
    private final GetUserTransactionsUseCase getUserTransactionsUseCase;
    private final GetUserTicketsUseCase getUserTicketsUseCase;

    @PostMapping("/register")
    public ResponseEntity<Response<LoginResponseDTO>> register(
            @Valid @RequestBody UserRegisterRequestDTO request,
            @RequestParam(required = false) String referralCode) {
        LoginResponseDTO response = registerUserUseCase.registerUser(request, referralCode);
        return Response.successfulResponse("Registration successful", response);
    }

    @GetMapping("/details")
    public ResponseEntity<Response<UserDetailsResponseDTO>> getUserDetails(@RequestParam UUID userId) {
        UserDetailsResponseDTO response = getUserDetailsUseCase.getUserDetails(userId);
        return Response.successfulResponse("User details retrieved successfully", response);
    }

    @PutMapping("/profile")
    public ResponseEntity<Response<UserResponseDTO>> updateProfile(
            @RequestParam UUID userId,
            @Valid @RequestBody UserUpdateRequestDTO request) {
        User updatedUser = updateUserProfileUseCase.execute(userId, request);
        return Response.successfulResponse("Profile updated successfully", UserResponseDTO.fromEntity(updatedUser));
    }

    @GetMapping("/points")
    public ResponseEntity<Response<UserPointsResponseDTO>> getPoints(@RequestParam UUID userId) {
        UserPointsResponseDTO response = getUserPointsUseCase.execute(userId);
        return Response.successfulResponse("Points retrieved successfully", response);
    }

    @PostMapping("/points/use")
    public ResponseEntity<Response<UsePointsResponseDTO>> usePoints(
            @RequestParam UUID userId,
            @Valid @RequestBody UsePointsRequestDTO request) {
        BigDecimal finalPrice = usePointsUseCase.execute(userId, request.getPoints(), request.getTicketPrice());

        UsePointsResponseDTO response = new UsePointsResponseDTO();
        response.setUserId(userId);
        response.setPointsUsed(request.getPoints());
        response.setOriginalPrice(request.getTicketPrice());
        response.setDiscountAmount(request.getTicketPrice().subtract(finalPrice));
        response.setFinalPrice(finalPrice);

        return Response.successfulResponse("Points used successfully", response);
    }

    @GetMapping("/transactions")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_CUSTOMER')")
    public ResponseEntity<Response<Page<TransactionResponseDTO>>> getUserTransactions(Pageable pageable) {
        return Response.successfulResponse(
                "Successfully retrieved user transactions",
                getUserTransactionsUseCase.getUserTransactions(pageable));
    }

    @GetMapping("/transactions/all")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_CUSTOMER')")
    public ResponseEntity<Response<List<TransactionResponseDTO>>> getAllUserTransactions() {
        return Response.successfulResponse(
                "Successfully retrieved all user transactions",
                getUserTransactionsUseCase.getUserTransactions());
    }

    @GetMapping("/tickets")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Response<Page<UserTicketResponseDTO>>> getUserTickets(Pageable pageable) {
        return Response.successfulResponse(
                "Successfully retrieved user tickets",
                getUserTicketsUseCase.getUserTickets(pageable));
    }

    @GetMapping("/tickets/all")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Response<List<UserTicketResponseDTO>>> getAllUserTickets() {
        return Response.successfulResponse(
                "Successfully retrieved all user tickets",
                getUserTicketsUseCase.getUserTickets());
    }
}
