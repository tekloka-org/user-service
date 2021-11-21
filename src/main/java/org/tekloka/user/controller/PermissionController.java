package org.tekloka.user.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tekloka.user.dto.PermissionDTO;
import org.tekloka.user.service.PermissionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping(value = "/permission")
public class PermissionController {

	private final PermissionService permissionService;
	
	PermissionController(PermissionService permissionService){
		this.permissionService = permissionService;
	}
	
	@Operation(summary = "Add Permission")
	@ApiResponses(value = {
	     @ApiResponse(responseCode = "200", description = "Permission added successfully"),
	     @ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
	@PostMapping(path = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> save(HttpServletRequest request, @RequestBody PermissionDTO permissionDTO) {
		return permissionService.save(request, permissionDTO);
	}
	
	@Operation(summary = "Update Permission")
	@ApiResponses(value = {
	     @ApiResponse(responseCode = "200", description = "Permission updated successfully"),
	     @ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
	@PostMapping(path = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> update(HttpServletRequest request, @RequestBody PermissionDTO permissionDTO) {
		return permissionService.update(request, permissionDTO);
	}
	
	@Operation(summary = "Delete Permission")
	@ApiResponses(value = {
	     @ApiResponse(responseCode = "200", description = "Permission deleted successfully"),
	     @ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
	@PostMapping(path = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> delete(HttpServletRequest request, @RequestBody PermissionDTO permissionDTO) {
		return permissionService.delete(request, permissionDTO);
	}
	
	@Operation(summary = "Get all permissions")
	@ApiResponses(value = {
	     @ApiResponse(responseCode = "200", description = "Permission list found"),
	     @ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
	@GetMapping(path = "get-all-permissions", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getAllActivePermissions(HttpServletRequest request) {
		 return permissionService.getAllActivePermissions(request);
	}
}
