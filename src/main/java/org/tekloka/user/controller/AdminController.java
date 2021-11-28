package org.tekloka.user.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tekloka.user.service.AdminService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping(value = "/admin")
public class AdminController {

	private final AdminService adminService;
	
	public AdminController(AdminService adminService) {
		this.adminService = adminService;
	}
	
//	@Operation(summary = "Clear Secutity Cache of a User")
//	@ApiResponses(value = {
//	     @ApiResponse(responseCode = "200", description = "Success"),
//	     @ApiResponse(responseCode = "500", description = "Internal Server Error")
//	})
//	@GetMapping(path = "clear-user-security-cache/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
//	@AllowFeignClient
//	public ResponseEntity<Object> getArticle(HttpServletRequest request, @PathVariable String userId) {
//		 return adminService.clearUserSecurityCache(request, userId);
//	}
}
