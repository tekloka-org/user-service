package org.tekloka.user.dto.mapper;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.tekloka.user.document.Permission;
import org.tekloka.user.document.Role;
import org.tekloka.user.dto.PermissionDTO;
import org.tekloka.user.dto.RoleDTO;
import org.tekloka.user.service.PermissionService;

@Component
public class RoleMapper {

	private final PermissionService permissionService;
	public RoleMapper(@Lazy PermissionService permissionService) {
		this.permissionService = permissionService;
	}
	
	public Role toRole(Optional<Role> roleOptional, RoleDTO roleDTO) {
		var role = new Role();
		if (roleOptional.isPresent()) {
			role = roleOptional.get();
		}
		role.setCode(roleDTO.getCode());
		role.setName(roleDTO.getName());
		
		Set<String> permissionIds = new HashSet<>();
		if(null != roleDTO.getPermissions()) {
			permissionIds = roleDTO.getPermissions().stream().map(PermissionDTO::getPermissionId).collect(Collectors.toSet());
		}
		Set<Permission> permissions = permissionService.findByPermissionIdInAndActive(permissionIds, true);
		if(null != permissions) {
			role.setPermissions(permissions);
		}
		
		return role;
	}
	
	public RoleDTO toRoleDTO(Role role) {
		var roleDTO = new RoleDTO();
		roleDTO.setRoleId(role.getRoleId());
		roleDTO.setCode(role.getCode());
		roleDTO.setName(role.getName());
		Set<PermissionDTO> permissionDTOs = permissionService.toPermissionDTOSet(role.getPermissions());
		if(null != permissionDTOs) {
			roleDTO.setPermissions(permissionDTOs);
		}
		return roleDTO;
	}

	public Set<RoleDTO> toRoleDTOSet(Set<Role> roles) {
		if (null != roles) {
			return roles.stream().map(this::toRoleDTO).collect(Collectors.toSet());
		} else {
			return new HashSet<>();
		}
	}
}
