package com.eventhub.dti.usecase.user;


import com.eventhub.dti.entity.User;
import com.eventhub.dti.infrastructure.user.dto.UserDetailResponseDTO;

import java.util.List;

public interface GetUserUseCase {
  List<User> getAllUsers();

  List<User> getAllUser();

  UserDetailResponseDTO getUserById(Long id);
}
