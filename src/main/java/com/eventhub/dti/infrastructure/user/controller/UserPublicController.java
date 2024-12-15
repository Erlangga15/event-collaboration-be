package com.eventhub.dti.infrastructure.user.controller;


import com.eventhub.dti.common.response.ApiResponse;
import com.eventhub.dti.entity.User;
import com.eventhub.dti.infrastructure.security.Claims;
import com.eventhub.dti.infrastructure.user.dto.BulkCreateUserRequestDTO;
import com.eventhub.dti.infrastructure.user.dto.CreateUserRequestDTO;
import com.eventhub.dti.infrastructure.user.dto.UserDetailResponseDTO;
import com.eventhub.dti.infrastructure.user.service.UserService;
import com.eventhub.dti.usecase.user.CreateUserUseCase;
import com.eventhub.dti.usecase.user.GetUserUseCase;
import jakarta.validation.Valid;
import lombok.extern.java.Log;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@Log
@RestController
@RequestMapping("/api/v1/users")
public class UserPublicController {
  private UserService userService;
  private GetUserUseCase getUsersUseCase;
  private CreateUserUseCase createUserUsecase;

  public void UsersPublicController(final UserService userService, GetUserUseCase getUsersUseCase, CreateUserUseCase createUserUsecase) {
    this.userService = userService;
    this.getUsersUseCase = getUsersUseCase;
    this.createUserUsecase = createUserUsecase;
  }

  @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
  @GetMapping
  public ResponseEntity<?> getUsers() {
    String email = Claims.getEmailFromJwt();
    log.info("Requester email is: " + email);
    return ApiResponse.successfulResponse("Get all users success", getUsersUseCase.getAllUsers());
  }

  @PostMapping
  public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserRequestDTO createUserRequestDTO) {
    User createdUser = userService.createUser(createUserRequestDTO);
    return ApiResponse.successfulResponse("Create new user success", createdUser);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getUser(@PathVariable final Long id) {
    return ApiResponse.successfulResponse("Get user details success", getUsersUseCase.getUserById(id));
  }

  @PostMapping("/bulk")
  public ResponseEntity<?> createUserBulk(@RequestBody BulkCreateUserRequestDTO req) {
    return ApiResponse.successfulResponse("Create new user success", createUserUsecase.bulkCreateUser(req));
  }

  @GetMapping("/{userId}/details")
  public ResponseEntity<?> getUserDetails(@PathVariable Long userId,
                                          @PageableDefault(size = 10) Pageable pageable) {
    UserDetailResponseDTO userDetails = userService.getUserDetails(userId, pageable);
    return ApiResponse.successfulResponse("Get user details success", userDetails);
  }

}
