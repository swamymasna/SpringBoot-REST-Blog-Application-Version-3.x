package com.swamy.service;

import java.util.List;

import com.swamy.dto.LoginDTO;
import com.swamy.dto.RegisterDTO;
import com.swamy.dto.UserDTO;

public interface IAuthService {

	public String registerUser(RegisterDTO registerDTO);

	public String loginUser(LoginDTO loginDTO);

	public List<UserDTO> getAllUsers();
}
