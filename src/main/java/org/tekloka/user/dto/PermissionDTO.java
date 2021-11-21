package org.tekloka.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissionDTO {
	
	private String permissionId;
	private String code;
	private String name;
	private boolean restricted;
	
}
