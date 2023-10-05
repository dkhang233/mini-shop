package com.dkhang.shopapplication.services;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.dkhang.shopapplication.dtos.UserDTO;
import com.dkhang.shopapplication.exceptionhandler.DataNotFoundException;
import com.dkhang.shopapplication.models.Role;
import com.dkhang.shopapplication.models.User;
import com.dkhang.shopapplication.repositories.RoleRepository;
import com.dkhang.shopapplication.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;

	@Override
	public User createUser(UserDTO userDTO) {

		if (userRepository.existsByPhoneNumber(userDTO.getPhoneNumber())) {
			throw new DataIntegrityViolationException("Phone number is exists");
		}

		Role role = roleRepository.findById(userDTO.getRoleId())
				.orElseThrow(() -> new DataNotFoundException("Not found Role with id = " + userDTO.getRoleId()));

		User newUser = User.builder().fullName(userDTO.getFullName()).password(userDTO.getPassword())
				.phoneNumber(userDTO.getPhoneNumber()).address(userDTO.getAddress()).birthDate(userDTO.getBirthDate())
				.isActive(true).facebookAccountId(userDTO.getFacebokAccountId())
				.googleAccountId(userDTO.getGoogleAccountId()).role(role).build();

		if (newUser.getFacebookAccountId() != 0 && newUser.getGoogleAccountId() != 0) {
			// newUser.setPassword(passEncoder.encoder(userDTO.getPassword))
			newUser.setPassword(userDTO.getPassword());
		}

		return userRepository.save(newUser);
	}

	@Override
	public String login(String phoneNumber, String password) {
		return null;
	}

	@Override
	public User updateUser(int id, UserDTO userDTO) {

		User user = userRepository.findById(id)
				.orElseThrow(() -> new DataNotFoundException("Not found user with id = " + id));

		if (!user.getPhoneNumber().equals(userDTO.getPhoneNumber())) {
			if (userRepository.existsByPhoneNumber(userDTO.getPhoneNumber())) {
				throw new DataIntegrityViolationException("Phone number is exists");
			}
		}

		Role role = roleRepository.findById(userDTO.getRoleId())
				.orElseThrow(() -> new DataNotFoundException("Not found Role with id = " + userDTO.getRoleId()));

		user.setFullName(userDTO.getFullName()).setPhoneNumber(userDTO.getPhoneNumber())
				.setPassword(userDTO.getPassword()).setAddress(userDTO.getAddress())
				.setBirthDate(userDTO.getBirthDate()).setFacebookAccountId(userDTO.getFacebokAccountId())
				.setGoogleAccountId(userDTO.getGoogleAccountId()).setRole(role);

		return userRepository.save(user);

	}

}
