package com.swamy.service.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.swamy.dto.LoginDTO;
import com.swamy.dto.RegisterDTO;
import com.swamy.dto.UserDTO;
import com.swamy.entity.Role;
import com.swamy.entity.User;
import com.swamy.exception.BlogApiException;
import com.swamy.exception.ResourceNotFoundException;
import com.swamy.repository.RoleRepository;
import com.swamy.repository.UserRepository;
import com.swamy.service.IAuthService;
import com.swamy.utils.AppConstants;

@Service
public class AuthServiceImpl implements IAuthService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public String registerUser(RegisterDTO registerDTO) {

		if (userRepository.existsByUsername(registerDTO.getUsername())) {
			throw new BlogApiException(AppConstants.USERNAME_ALREADY_AVAILABLE);
		} else if (userRepository.existsByEmail(registerDTO.getEmail())) {
			throw new BlogApiException(AppConstants.EMAIL_ALREADY_AVAILABLE);
		}

		User user = new User();
		user.setName(registerDTO.getName());
		user.setUsername(registerDTO.getUsername());
		user.setEmail(registerDTO.getEmail());
		user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

		Role role = roleRepository.findByName(AppConstants.ROLE_NAME)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.ROLE_NOT_FOUND_EXCEPTION));
		user.setRoles(Set.of(role));

		userRepository.save(user);

		return AppConstants.USER_REG_SUCCESS;
	}

	@Override
	public String loginUser(LoginDTO loginDTO) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginDTO.getUsernameOrEmail(), loginDTO.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		return AppConstants.USER_LOGIN_SUCCESS;
	}

	@Override
	public List<UserDTO> getAllUsers() {

		return userRepository.findAll().stream().map(user -> modelMapper.map(user, UserDTO.class))
				.collect(Collectors.toList());

	}

}
