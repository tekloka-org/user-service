package org.tekloka.user.dto.mapper;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.tekloka.user.document.Permission;
import org.tekloka.user.dto.PermissionDTO;

@Component
public class PermissionMapper {
	

	public Permission toPermission(Optional<Permission> permissionOptional, PermissionDTO permissionDTO) {
		var permission = new Permission();
		if(permissionOptional.isPresent()) {
			permission = permissionOptional.get();
		}
		permission.setCode(permissionDTO.getCode());
		permission.setName(permissionDTO.getName());
		return permission;
	}
	
	public PermissionDTO toPermissionDTO(Permission permission) {
		var permissionDTO = new PermissionDTO();
		permissionDTO.setPermissionId(permission.getPermissionId());
		permissionDTO.setCode(permission.getCode());
		permissionDTO.setName(permission.getName());
		return permissionDTO;
	}
	
	public Set<PermissionDTO> toPermissionDTOSet(Set<Permission> permissions){
		if(null != permissions) {
			return permissions.stream().map(this::toPermissionDTO).collect(Collectors.toSet());	
		}else {
			return new HashSet<>();
		}
	}
}
