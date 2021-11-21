package org.tekloka.user.security;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class UserAccess{
	private String userId;
	private Set<String> roleKeys;
	private Set<String> permissionKeys;
	
	public UserAccess(String userId, Set<String> roleKeys, Set<String> permissionKeys) {
		this.userId = userId;
		this.roleKeys = roleKeys;
		this.permissionKeys = permissionKeys;
	}
}