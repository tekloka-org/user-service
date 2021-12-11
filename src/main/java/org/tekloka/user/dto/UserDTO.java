package org.tekloka.user.dto;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

	private String userId;
	private String name;
	private String emailAddress;
	private String password;
	private boolean verified;
	private Set<RoleDTO> roles;
	
}
