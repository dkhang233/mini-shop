package com.dkhang.shopapplication.services;

import com.dkhang.shopapplication.dtos.UserDTO;
import com.dkhang.shopapplication.models.User;

public interface UserService {

	User createUser(UserDTO userDTO);

	String login(String phoneNumber, String password);

	User updateUser(int id, UserDTO userDTO);
}
