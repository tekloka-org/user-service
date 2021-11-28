package org.tekloka.user.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.tekloka.user.feign.ArticleServiceProxy;
import org.tekloka.user.security.SecurityCache;
import org.tekloka.user.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService{

	private final SecurityCache securityCache;
	private final ArticleServiceProxy articleServiceProxy;
	
	@Value("${feign.client.access.id}") 
	private String feignClientAccessId;
	
	public AdminServiceImpl(SecurityCache securityCache,
			ArticleServiceProxy articleServiceProxy) {
		this.securityCache = securityCache;
		this.articleServiceProxy = articleServiceProxy;
	}
	
	@Override
	public void removeUserFromSecurityCache(String userId){
		securityCache.removeUserFromSecurityCache(userId);
		articleServiceProxy.removeUserFromSecurityCache(feignClientAccessId, userId);
	}

	@Override
	public void clearSecurityCache() {
		securityCache.clearSecurityCache();
		articleServiceProxy.clearSecurityCache(feignClientAccessId);
	}

	@Override
	public ResponseEntity<Object> getUserAccess(HttpServletRequest request, String userId) {
		return ResponseEntity.ok(securityCache.getUserAccess(userId));
	}
}
