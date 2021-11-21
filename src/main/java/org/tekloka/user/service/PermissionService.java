package org.tekloka.user.service;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.tekloka.user.document.Permission;
import org.tekloka.user.dto.PermissionDTO;

public interface PermissionService {

	ResponseEntity<Object> save(HttpServletRequest request, PermissionDTO permissionDTO);

	ResponseEntity<Object> update(HttpServletRequest request, PermissionDTO permissionDTO);

	ResponseEntity<Object> delete(HttpServletRequest request, PermissionDTO permissionDTO);

	ResponseEntity<Object> getAllActivePermissions(HttpServletRequest request);

	Set<Permission> findByPermissionIdInAndActive(Set<String> permissionIds, boolean active);

	Set<PermissionDTO> toPermissionDTOSet(Set<Permission> permissions);


}
