package org.tekloka.user.service;

import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.tekloka.user.document.Role;
import org.tekloka.user.dto.RoleDTO;

public interface RoleService {

	ResponseEntity<Object> save(HttpServletRequest request, RoleDTO roleDTO);

	ResponseEntity<Object> update(HttpServletRequest request, RoleDTO roleDTO);

	ResponseEntity<Object> delete(HttpServletRequest request, RoleDTO roleDTO);

	ResponseEntity<Object> getAllActiveRoles(HttpServletRequest request);

	ResponseEntity<Object> getRole(HttpServletRequest request, String roleCode);

	Optional<Role> findByRoleIdOrCode(String roleId, String code);

	RoleDTO toRoleDTO(Role role);

	Set<Role> findByRoleIdInAndActive(Set<String> roleIds, boolean active);

	Set<RoleDTO> toRoleDTOSet(Set<Role> roles);

}
