package com.eventhub.dti.infrastructure.user.controller;


import com.eventhub.dti.common.response.ApiResponse;
import com.eventhub.dti.infrastructure.security.Claims;
import com.eventhub.dti.infrastructure.user.dto.BulkCreateUserRequestDTO;
import com.eventhub.dti.infrastructure.user.dto.CreateUserRequestDTO;
import com.eventhub.dti.usecase.user.CreateUserUseCase;
import com.eventhub.dti.usecase.user.GetUserUseCase;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Log
@RestController
@RequestMapping("/api/v1/user")
public class UserPublicController {
  private GetUserUseCase getUsersUseCase;
  private CreateUserUseCase createUserUsecase;

  public void UsersPublicController(final GetUserUseCase getUsersUseCase, CreateUserUseCase createUserUsecase) {
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

  @GetMapping("/{id}")
  public ResponseEntity<?> getUser(@PathVariable final Long id) {
    return ApiResponse.successfulResponse("Get user details success", getUsersUseCase.getUserById(id));
  }

  @PostMapping("/register")
  public ResponseEntity<?> createUser(@RequestBody CreateUserRequestDTO req) {
    var result = createUserUsecase.createUser(req);
    return ApiResponse.successfulResponse("Create new user success", result);
  }

  @PostMapping("/bulk")
  public ResponseEntity<?> createUserBulk(@RequestBody BulkCreateUserRequestDTO req) {
    return ApiResponse.successfulResponse("Create new user success", createUserUsecase.bulkCreateUser(req));
  }
}
