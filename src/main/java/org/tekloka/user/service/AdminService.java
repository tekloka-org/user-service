package org.tekloka.user.service;

public interface AdminService {

	void removeUserFromSecurityCache(String userId);
	void clearSecurityCache();
}
