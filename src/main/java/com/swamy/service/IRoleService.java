package com.swamy.service;

import java.util.List;

import com.swamy.dto.RoleDTO;

public interface IRoleService {

	public RoleDTO saveRole(RoleDTO roleDTO);

	public List<RoleDTO> getAllRoles();
}
