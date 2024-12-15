package com.eventhub.dti.usecase.user;

import com.eventhub.dti.entity.User;
import com.eventhub.dti.infrastructure.user.dto.BulkCreateUserRequestDTO;
import com.eventhub.dti.infrastructure.user.dto.CreateUserRequestDTO;
import com.eventhub.dti.infrastructure.user.dto.UserDetailResponseDTO;

import java.util.List;

public interface CreateUserUseCase{
  UserDetailResponseDTO createUser(CreateUserRequestDTO req);
  User createUserWithEntity(User req);
  List<User> bulkCreateUser(BulkCreateUserRequestDTO req);
}
