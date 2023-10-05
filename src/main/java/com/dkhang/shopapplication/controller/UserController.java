package com.dkhang.shopapplication.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dkhang.shopapplication.dtos.UserDTO;
import com.dkhang.shopapplication.dtos.UserLoginDTO;
import com.dkhang.shopapplication.services.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@PostMapping("/register")
	public ResponseEntity<String> register(@Valid @RequestBody UserDTO userDTO) {

		userService.createUser(userDTO);
		return new ResponseEntity<String>("Create user successfully", HttpStatus.CREATED);

	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@Valid @RequestBody UserLoginDTO userLoginDTO) {
		userService.login(userLoginDTO.getPhoneNumber(), userLoginDTO.getPassword());
		return ResponseEntity
				.ok("User: " + userLoginDTO.getPhoneNumber() + " ; Password: " + userLoginDTO.getPassword());
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<String> update(@PathVariable int id, @Valid @RequestBody UserDTO userDTO) {

		userService.updateUser(id,userDTO);
		return new ResponseEntity<String>("Update user successfully", HttpStatus.OK);

	}
}
