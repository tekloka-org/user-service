package org.tekloka.user.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.tekloka.user.constants.AppConstants;
import org.tekloka.user.constants.DataConstants;
import org.tekloka.user.constants.ResponseConstants;
import org.tekloka.user.document.Permission;
import org.tekloka.user.dto.PermissionDTO;
import org.tekloka.user.dto.mapper.PermissionMapper;
import org.tekloka.user.repository.PermissionRepository;
import org.tekloka.user.service.PermissionService;
import org.tekloka.user.util.ResponseUtil;

@Service
public class PermissionServiceImpl implements PermissionService{

	private final Logger logger = LoggerFactory.getLogger(PermissionServiceImpl.class);
	private final PermissionRepository permissionRepository;
	private final PermissionMapper permissionMapper;
	private final ResponseUtil responseUtil;
	
	public PermissionServiceImpl(PermissionRepository permissionRepository, PermissionMapper permissionMapper,
			ResponseUtil responseUtil) {
		this.permissionRepository = permissionRepository;
		this.permissionMapper = permissionMapper;
		this.responseUtil = responseUtil;
	}

	public Permission save(Permission permission) {
		return permissionRepository.save(permission);
	}

	public Permission toPermission(Optional<Permission> permissionOptional, PermissionDTO permissionDTO) {
		return permissionMapper.toPermission(permissionOptional, permissionDTO);
	}
	
	public PermissionDTO toPermissionDTO(Permission permission) {
		return permissionMapper.toPermissionDTO(permission);
	}
	
	@Override
	public Set<PermissionDTO> toPermissionDTOSet(Set<Permission> permissions) {
		return permissionMapper.toPermissionDTOSet(permissions);
	}
	
	public Optional<Permission> findByPermissionId(String permissionId){
		return permissionRepository.findById(permissionId);
	}
	
	public Set<Permission> findByActive(boolean active){
		return permissionRepository.findByActive(active);
	}
	
	public Set<Permission> findByActiveAndRestricted(boolean active, boolean restricted){
		return permissionRepository.findByActiveAndRestricted(active, restricted);
	}
	
	@Override
	public Set<Permission> findByPermissionIdInAndActive(Set<String> permissionIds, boolean active){
		return permissionRepository.findByPermissionIdInAndActive(permissionIds, active);
	}

	@Override
	public ResponseEntity<Object> save(HttpServletRequest request, PermissionDTO permissionDTO) {
		Map<String, Object> dataMap = new HashMap<>();
		try {
			Optional<Permission> permissionOptional = Optional.empty();
			var permission = toPermission(permissionOptional, permissionDTO);
			permission.setActive(true);
			permission = save(permission);
			dataMap.put(DataConstants.PERMISSION, toPermissionDTO(permission));
			return responseUtil.generateResponse(dataMap, ResponseConstants.PERMISSION_SAVED);
		}catch (Exception e) {
			logger.error(AppConstants.LOG_FORMAT, ResponseConstants.PERMISSION_NOT_SAVED, e);
			return responseUtil.generateResponse(dataMap, ResponseConstants.PERMISSION_NOT_SAVED);
		}
	}

	@Override
	public ResponseEntity<Object> update(HttpServletRequest request, PermissionDTO permissionDTO) {
		Map<String, Object> dataMap = new HashMap<>();
		Optional<Permission> permissionOptional = findByPermissionId(permissionDTO.getPermissionId());		
		if(permissionOptional.isPresent()) {
			try {
				var permission = toPermission(permissionOptional, permissionDTO);
				permission = save(permission);
				dataMap.put(DataConstants.PERMISSION, toPermissionDTO(permission));
				return responseUtil.generateResponse(dataMap, ResponseConstants.PERMISSION_UPDATED);
			}catch (Exception e) {
				logger.error(AppConstants.LOG_FORMAT, ResponseConstants.PERMISSION_NOT_UPDATED, e);
				return responseUtil.generateResponse(dataMap, ResponseConstants.PERMISSION_NOT_UPDATED);
			}
		}else {
			return responseUtil.generateResponse(dataMap, ResponseConstants.PERMISSION_NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<Object> delete(HttpServletRequest request, PermissionDTO permissionDTO) {
		Map<String, Object> dataMap = new HashMap<>();
		Optional<Permission> permissionOptional = findByPermissionId(permissionDTO.getPermissionId());		
		if(permissionOptional.isPresent()) {
			try {
				var permission = permissionOptional.get();
				permission.setActive(false);
				save(permission);
				return responseUtil.generateResponse(dataMap, ResponseConstants.PERMISSION_DELETED);
			}catch (Exception e) {
				logger.error(AppConstants.LOG_FORMAT, ResponseConstants.PERMISSION_NOT_DELETED, e);
				return responseUtil.generateResponse(dataMap, ResponseConstants.PERMISSION_NOT_DELETED);
			}
		}else {
			return responseUtil.generateResponse(dataMap, ResponseConstants.PERMISSION_NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<Object> getAllActivePermissions(HttpServletRequest request) {
		Map<String, Object> dataMap = new HashMap<>();
		Set<Permission> permissions = findByActive(true);
		dataMap.put(DataConstants.PERMISSION_LIST, toPermissionDTOSet(permissions));
		return responseUtil.generateResponse(dataMap, ResponseConstants.PERMISSION_LIST_FOUND);
	}
	

}
