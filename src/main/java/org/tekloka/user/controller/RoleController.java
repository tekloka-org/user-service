package org.tekloka.user.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tekloka.user.constants.PermissionConstants;
import org.tekloka.user.dto.RoleDTO;
import org.tekloka.user.security.AccessPermissions;
import org.tekloka.user.service.RoleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping(value = "/role")
public class RoleController {

	private final RoleService roleService;
	
	RoleController(RoleService roleService){
		this.roleService = roleService;
	}
	
	@Operation(summary = "Add Role")
	@ApiResponses(value = {
	     @ApiResponse(responseCode = "200", description = "Role added successfully"),
	     @ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
	@PostMapping(path = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
	@AccessPermissions(value = PermissionConstants.UPDATE_ROLE )
	public ResponseEntity<Object> save(HttpServletRequest request, @RequestBody RoleDTO roleDTO) {
		return roleService.save(request, roleDTO);
	}
	
	@Operation(summary = "Update Role")
	@ApiResponses(value = {
	     @ApiResponse(responseCode = "200", description = "Role updated successfully"),
	     @ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
	@PostMapping(path = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> update(HttpServletRequest request, @RequestBody RoleDTO roleDTO) {
		return roleService.update(request, roleDTO);
	}
	
	@Operation(summary = "Delete Role")
	@ApiResponses(value = {
	     @ApiResponse(responseCode = "200", description = "Role deleted successfully"),
	     @ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
	@PostMapping(path = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> delete(HttpServletRequest request, @RequestBody RoleDTO roleDTO) {
		return roleService.delete(request, roleDTO);
	}
	
	@Operation(summary = "Get all Roles")
	@ApiResponses(value = {
	     @ApiResponse(responseCode = "200", description = "Role list found"),
	     @ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
	@GetMapping(path = "get-all-roles", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getAllActiveRoles(HttpServletRequest request) {
		 return roleService.getAllActiveRoles(request);
	}
	
	@Operation(summary = "Get Role by Code or Id")
	@ApiResponses(value = {
	     @ApiResponse(responseCode = "200", description = "Role found"),
	     @ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
	@GetMapping(path = "get-role/{roleCode}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getRole(HttpServletRequest request, @PathVariable String roleCode) {
		 return roleService.getRole(request, roleCode);
	}
}
