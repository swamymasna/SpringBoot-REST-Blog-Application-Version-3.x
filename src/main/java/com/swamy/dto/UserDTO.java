package com.swamy.dto;

import java.util.Set;

import com.swamy.entity.Role;

import lombok.Data;

@Data
public class UserDTO {

	private Integer id;
	private String name;
	private String username;
	private String email;
	private String password;
	private Set<Role> roles;
}
