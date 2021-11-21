package org.tekloka.user.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tekloka.user.dto.LoginDTO;
import org.tekloka.user.dto.SignUpDTO;
import org.tekloka.user.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping(path = "/public")
public class PublicController {

	private final UserService userService;

	public PublicController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping(path = "/check-services-status", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> checkServicesStatus() {
		return userService.checkServicesStatus();
	}

	@Operation(summary = "Sign Up service for new Users")
	@ApiResponses(value = {
	     @ApiResponse(responseCode = "200", description = "Sign Up success"),
	     @ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
	@PostMapping(path = "/sign-up", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> signUp(HttpServletRequest request, @RequestBody SignUpDTO signUpDTO) {
		return userService.signUp(request, signUpDTO);
	}

	@PostMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> login(HttpServletRequest request, @RequestBody LoginDTO loginDTO){
		return userService.login(request, loginDTO);
	}

}
