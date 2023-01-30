package com.swamy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swamy.dto.LoginDTO;
import com.swamy.dto.RegisterDTO;
import com.swamy.dto.UserDTO;
import com.swamy.service.IAuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {

	@Autowired
	private IAuthService authService;

	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody RegisterDTO registerDTO) {
		return new ResponseEntity<String>(authService.registerUser(registerDTO), HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ResponseEntity<String> loginUser(@RequestBody LoginDTO loginDTO) {
		return new ResponseEntity<String>(authService.loginUser(loginDTO), HttpStatus.OK);
	}

	@GetMapping("/list")
	public ResponseEntity<List<UserDTO>> getAllUsers() {
		return new ResponseEntity<>(authService.getAllUsers(), HttpStatus.OK);
	}
}
