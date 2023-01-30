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

import com.swamy.dto.RoleDTO;
import com.swamy.service.IRoleService;

@RestController
@RequestMapping("/api/v1")
public class RoleRestController {

	@Autowired
	private IRoleService roleService;

	@PostMapping("/role/save")
	public ResponseEntity<RoleDTO> saveRole(@RequestBody RoleDTO roleDTO) {
		return new ResponseEntity<RoleDTO>(roleService.saveRole(roleDTO), HttpStatus.CREATED);
	}

	@GetMapping("/role/list")
	public ResponseEntity<List<RoleDTO>> getAllRoles() {
		return new ResponseEntity<>(roleService.getAllRoles(), HttpStatus.OK);
	}
}
