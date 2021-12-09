package org.tekloka.user.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.tekloka.user.feign.ArticleServiceProxy;
import org.tekloka.user.feign.CategoryServiceProxy;
import org.tekloka.user.feign.DiscussionServiceProxy;
import org.tekloka.user.security.SecurityCache;
import org.tekloka.user.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService{

	private final SecurityCache securityCache;
	private final ArticleServiceProxy articleServiceProxy;
	private final CategoryServiceProxy categoryServiceProxy;
	private final DiscussionServiceProxy discussionServiceProxy;
	
	@Value("${feign.client.access.id}") 
	private String feignClientAccessId;
	
	public AdminServiceImpl(SecurityCache securityCache,
			ArticleServiceProxy articleServiceProxy, CategoryServiceProxy categoryServiceProxy,
			DiscussionServiceProxy discussionServiceProxy) {
		this.securityCache = securityCache;
		this.articleServiceProxy = articleServiceProxy;
		this.categoryServiceProxy = categoryServiceProxy;
		this.discussionServiceProxy = discussionServiceProxy;
	}
	
	@Override
	public void removeUserFromSecurityCache(String userId){
		securityCache.removeUserFromSecurityCache(userId);
		articleServiceProxy.removeUserFromSecurityCache(feignClientAccessId, userId);
		categoryServiceProxy.removeUserFromSecurityCache(feignClientAccessId, userId);
		discussionServiceProxy.removeUserFromSecurityCache(feignClientAccessId, userId);
	}

	@Override
	public void clearSecurityCache() {
		securityCache.clearSecurityCache();
		articleServiceProxy.clearSecurityCache(feignClientAccessId);
		categoryServiceProxy.clearSecurityCache(feignClientAccessId);
		discussionServiceProxy.clearSecurityCache(feignClientAccessId);
	}

	@Override
	public ResponseEntity<Object> getUserAccess(HttpServletRequest request, String userId) {
		return ResponseEntity.ok(securityCache.getUserAccess(userId));
	}
}
