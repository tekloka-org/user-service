package org.tekloka.user.dto;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleDTO  {
	private String roleId;
	private String code;
	private String name;
    private boolean restricted;
    private Set<PermissionDTO> permissions;
}