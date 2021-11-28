package org.tekloka.user.security;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.tekloka.user.document.Role;
import org.tekloka.user.document.User;
import org.tekloka.user.service.UserService;

@Component
public final class SecurityCache {

	private static Map<String, UserAccess> userAccessMap = new HashMap<>();
	
	private final UserService userService;
	
	public SecurityCache(@Lazy UserService userService) {
		this.userService = userService;
	}
	
	public UserAccess getUserAccess(String userId){
		if(userAccessMap.containsKey(userId)) {
			return userAccessMap.get(userId);
		}else {
			var userAccess = addUserToAccessMap(userId); 
			if(null != userAccess) {
				return userAccess;
			}
			return null;
		}
	}
	
	public Set<String> getUserPermissionSet(String userId){
		if(userAccessMap.containsKey(userId)) {
			return userAccessMap.get(userId).getPermissionKeys();
		}else {
			var userAccess = addUserToAccessMap(userId); 
			if(null != userAccess) {
				return userAccess.getPermissionKeys();
			}
			return new HashSet<>();
		}
	}
	
	public Set<String> getUserRoleSet(String userId){
		if(userAccessMap.containsKey(userId)) {
			return userAccessMap.get(userId).getRoleKeys();
		}else {
			var userAccess = addUserToAccessMap(userId); 
			if(null != userAccess) {
				return userAccess.getRoleKeys();
			}
			return new HashSet<>();
		}
	}
	
	public UserAccess addUserToAccessMap(String userId) {
		Optional<User> userOptional = userService.findByUserId(userId);
		if(userOptional.isPresent()) {
			Set<String> userRoles = new HashSet<>();
			Set<String> userPermissions = new HashSet<>();
			if(null != userOptional.get().getRoles()) {
				userOptional.get().getRoles().stream().filter(Objects::nonNull).forEach(e -> userRoles.add(e.getCode()));
				userOptional.get().getRoles().stream().filter(Objects::nonNull).map(Role::getPermissions)
				.forEach(permissions -> permissions.stream().forEach(p -> userPermissions.add(p.getCode())));
			}
			var userAccess = new UserAccess(userId, userOptional.get().getName(), userRoles, userPermissions);
			userAccessMap.put(userId, userAccess);
			return userAccess;
		}
		return null;
	}
	
	public boolean removeUserFromSecurityCache(String userId) {
		if(userAccessMap.containsKey(userId)) {
			userAccessMap.remove(userId);
			return true;
		}else {
			return false;
		}
	}
	
	public void clearSecurityCache() {
		userAccessMap.clear();
	}
	
	
}


