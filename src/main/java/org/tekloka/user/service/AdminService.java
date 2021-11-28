package org.tekloka.user.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

public interface AdminService {

	void removeUserFromSecurityCache(String userId);
	void clearSecurityCache();
	ResponseEntity<Object> getUserAccess(HttpServletRequest request, String userId);
	
}
