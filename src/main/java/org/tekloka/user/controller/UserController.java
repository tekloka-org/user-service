package org.tekloka.user.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tekloka.user.constants.DataConstants;
import org.tekloka.user.dto.UserDTO;
import org.tekloka.user.security.AllowFeignClient;
import org.tekloka.user.security.UserAccess;
import org.tekloka.user.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping(value = "/user")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@Operation(summary = "Add User")
	@ApiResponses(value = {
	     @ApiResponse(responseCode = "200", description = "User added successfully"),
	     @ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
	@PostMapping(path = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> save(HttpServletRequest request, @RequestBody UserDTO userDTO) {
		return userService.save(request, userDTO);
	}
	
	@Operation(summary = "Update User")
	@ApiResponses(value = {
	     @ApiResponse(responseCode = "200", description = "User updated successfully"),
	     @ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
	@PostMapping(path = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> update(HttpServletRequest request, @RequestBody UserDTO userDTO) {
		return userService.update(request, userDTO);
	}
	
	@Operation(summary = "Delete User")
	@ApiResponses(value = {
	     @ApiResponse(responseCode = "200", description = "User deleted successfully"),
	     @ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
	@PostMapping(path = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> delete(HttpServletRequest request, @RequestBody UserDTO userDTO) {
		return userService.delete(request, userDTO);
	}
	
	@Operation(summary = "Get all Users")
	@ApiResponses(value = {
	     @ApiResponse(responseCode = "200", description = "User list found"),
	     @ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
	@GetMapping(path = "get-all-users", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getAllActiveUsers(HttpServletRequest request) {
		 return userService.getAllActiveUsers(request);
	}
	
	@Operation(summary = "Get User by userId")
	@ApiResponses(value = {
	     @ApiResponse(responseCode = "200", description = "User found"),
	     @ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
	@GetMapping(path = "get-user/{userIdentifier}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getRole(HttpServletRequest request, @PathVariable String userIdentifier) {
		 return userService.getUser(request, userIdentifier);
	}
	
	@Operation(summary = "Get Logged In User Details")
	@ApiResponses(value = {
	     @ApiResponse(responseCode = "200", description = "User found"),
	     @ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
	@GetMapping(path = "get-logged-in-user", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getLoggedInUser(HttpServletRequest request) {
		 return userService.getLoggedInUser(request);
	}
	
	@Operation(summary = "Get User Access. Works only for Feign clients")
	@ApiResponses(value = {
	     @ApiResponse(responseCode = "200", description = "Success"),
	     @ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
	@GetMapping(path = "get-user-access/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@AllowFeignClient
	public ResponseEntity<Object>  getUserAccess(HttpServletRequest request, 
			//@RequestHeader(value = DataConstants.FEIGN_CLIENT_ACCESS_ID, required = true) String feignClientAccessId,
			@PathVariable String userId) {
		//System.out.println("feignClientAccessId: "+feignClientAccessId);
		 return userService.getUserAccess(request, userId);
	}
}
