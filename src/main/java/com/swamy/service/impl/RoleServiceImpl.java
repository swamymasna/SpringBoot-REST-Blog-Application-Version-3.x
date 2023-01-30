package com.swamy.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swamy.dto.RoleDTO;
import com.swamy.entity.Role;
import com.swamy.repository.RoleRepository;
import com.swamy.service.IRoleService;

@Service
public class RoleServiceImpl implements IRoleService {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public RoleDTO saveRole(RoleDTO roleDTO) {
		Role role = modelMapper.map(roleDTO, Role.class);
		Role savedRole = roleRepository.save(role);
		RoleDTO roleResponse = modelMapper.map(savedRole, RoleDTO.class);
		return roleResponse;
	}

	@Override
	public List<RoleDTO> getAllRoles() {
		List<Role> rolesList = roleRepository.findAll();
		return rolesList.stream().map(role -> modelMapper.map(role, RoleDTO.class)).collect(Collectors.toList());
	}

}
