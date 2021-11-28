package org.tekloka.user.security;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserAccess{
	private String userId;
	private String userName;
	private Set<String> roleKeys;
	private Set<String> permissionKeys;
	
}