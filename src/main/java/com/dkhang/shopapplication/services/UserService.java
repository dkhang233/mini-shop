package com.dkhang.shopapplication.services;

import com.dkhang.shopapplication.dtos.UserDTO;
import com.dkhang.shopapplication.dtos.UserLoginDTO;
import com.dkhang.shopapplication.models.User;
import com.dkhang.shopapplication.responses.AuthenticationResponse;

public interface UserService {

	AuthenticationResponse createUser(UserDTO userDTO);

	AuthenticationResponse login(UserLoginDTO userLoginDTO);

	User updateUser(int id, UserDTO userDTO);
}
