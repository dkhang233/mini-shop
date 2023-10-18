package com.dkhang.shopapplication.services;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dkhang.shopapplication.dtos.UserDTO;
import com.dkhang.shopapplication.dtos.UserLoginDTO;
import com.dkhang.shopapplication.exceptionhandler.DataAlreadyExistsException;
import com.dkhang.shopapplication.exceptionhandler.DataNotFoundException;
import com.dkhang.shopapplication.models.Role;
import com.dkhang.shopapplication.models.User;
import com.dkhang.shopapplication.repositories.RoleRepository;
import com.dkhang.shopapplication.repositories.UserRepository;
import com.dkhang.shopapplication.responses.AuthenticationResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final TokenService tokenService;
	private final AuthenticationManager authenticationManager;

	@Override
	public AuthenticationResponse createUser(UserDTO userDTO) {

		if (userRepository.existsByPhoneNumber(userDTO.getPhoneNumber())) {
			throw new DataAlreadyExistsException("Phone number is exists");
		}

		Role role = roleRepository.findById(userDTO.getRoleId())
				.orElseThrow(() -> new DataNotFoundException("Not found Role with id = " + userDTO.getRoleId()));

		User newUser = User.builder()
						.fullName(userDTO.getFullName())
						.phoneNumber(userDTO.getPhoneNumber())
						.address(userDTO.getAddress()).birthDate(userDTO.getBirthDate())
						.isActive(true)
						.facebookAccountId(userDTO.getFacebokAccountId())
						.googleAccountId(userDTO.getGoogleAccountId())
						.role(role)
						.build();

		if (newUser.getFacebookAccountId() == 0 && newUser.getGoogleAccountId() == 0) {
			newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		}

		UserDetails userDetails = userRepository.save(newUser);
		String generateToken = jwtService.generateToken(userDetails);
		tokenService.createUserToken((User)userDetails, generateToken);
		return new AuthenticationResponse(generateToken);
	}

	@Override
	public AuthenticationResponse login(UserLoginDTO userLoginDTO) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginDTO.getPhoneNumber(),userLoginDTO.getPassword()));
		UserDetails userDetails = userRepository.findByPhoneNumber(userLoginDTO.getPhoneNumber())
																		.orElseThrow(() -> new DataNotFoundException("Phone number or password is incorrect"));
		String generateToken = jwtService.generateToken(userDetails);
		tokenService.revokeAllToken((User)userDetails);
		tokenService.createUserToken((User)userDetails, generateToken);
		return new AuthenticationResponse(generateToken);
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

		user.setFullName(userDTO.getFullName())
			.setPhoneNumber(userDTO.getPhoneNumber())
			.setAddress(userDTO.getAddress())
			.setBirthDate(userDTO.getBirthDate())
			.setFacebookAccountId(userDTO.getFacebokAccountId())
			.setGoogleAccountId(userDTO.getGoogleAccountId())
			.setRole(role);
		
		if (user.getFacebookAccountId() == 0 && user.getGoogleAccountId() == 0) {
			user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		}

		return userRepository.save(user);

	}

}
