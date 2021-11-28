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
import org.tekloka.user.document.Role;
import org.tekloka.user.dto.RoleDTO;
import org.tekloka.user.dto.mapper.RoleMapper;
import org.tekloka.user.repository.RoleRepository;
import org.tekloka.user.service.AdminService;
import org.tekloka.user.service.RoleService;
import org.tekloka.user.util.ResponseUtil;

@Service
public class RoleServiceImpl implements RoleService{

	private final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);
	private final RoleRepository roleRepository;
	private final RoleMapper roleMapper;
	private final ResponseUtil responseUtil;
	private final AdminService adminService;
	
	public RoleServiceImpl(RoleRepository rolerepository, RoleMapper roleMapper, ResponseUtil responseUtil,
			AdminService adminService) {
	        this.roleRepository = rolerepository;
	        this.roleMapper = roleMapper;
	        this.responseUtil = responseUtil;
	        this.adminService = adminService;
	    }

	public Role save(Role role) {
		return roleRepository.save(role);
	}

	public Role toRole(Optional<Role> roleOptional, RoleDTO roleDTO) {
		return roleMapper.toRole(roleOptional, roleDTO);
	}

	@Override
	public RoleDTO toRoleDTO(Role role) {
		return roleMapper.toRoleDTO(role);
	}

	@Override
	public Set<RoleDTO> toRoleDTOSet(Set<Role> roles) {
		return roleMapper.toRoleDTOSet(roles);
	}

	public Optional<Role> findByRoleId(String roleId) {
		return roleRepository.findById(roleId);
	}

	public Set<Role> findByActive(boolean active) {
		return roleRepository.findByActive(active);
	}

	public Set<Role> findByActiveAndRestricted(boolean active, boolean restricted) {
		return roleRepository.findByActiveAndRestricted(active, restricted);
	}
	
	@Override
	public Set<Role> findByRoleIdInAndActive(Set<String> roleIds, boolean active){
		return roleRepository.findByRoleIdInAndActive(roleIds, active);
	}
	
	@Override
	public Optional<Role> findByRoleIdOrCode(String roleId, String code){
		return roleRepository.findByRoleIdOrCode(roleId, code);
	}
	
	@Override
	public ResponseEntity<Object> save(HttpServletRequest request, RoleDTO roleDTO) {
		Map<String, Object> dataMap = new HashMap<>();
		try {
			Optional<Role> roleOptional = Optional.empty();
			var role = toRole(roleOptional, roleDTO);
			role.setActive(true);
			role = save(role);
			dataMap.put(DataConstants.ROLE, toRoleDTO(role));
			return responseUtil.generateResponse(dataMap, ResponseConstants.ROLE_SAVED);
		}catch (Exception e) {
			logger.error(AppConstants.LOG_FORMAT, ResponseConstants.ROLE_NOT_SAVED, e);
			return responseUtil.generateResponse(dataMap, ResponseConstants.ROLE_NOT_SAVED);
		}
	}

	@Override
	public ResponseEntity<Object> update(HttpServletRequest request, RoleDTO roleDTO) {
		Map<String, Object> dataMap = new HashMap<>();
		Optional<Role> roleOptional = findByRoleId(roleDTO.getRoleId());		
		if(roleOptional.isPresent()) {
			try {
				var role = toRole(roleOptional, roleDTO);
				role = save(role);
				dataMap.put(DataConstants.ROLE, toRoleDTO(role));
				adminService.clearSecurityCache();
				return responseUtil.generateResponse(dataMap, ResponseConstants.ROLE_UPDATED);
			}catch (Exception e) {
				logger.error(AppConstants.LOG_FORMAT, ResponseConstants.ROLE_NOT_UPDATED, e);
				return responseUtil.generateResponse(dataMap, ResponseConstants.ROLE_NOT_UPDATED);
			}
		}else {
			return responseUtil.generateResponse(dataMap, ResponseConstants.ROLE_NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<Object> delete(HttpServletRequest request, RoleDTO roleDTO) {
		Map<String, Object> dataMap = new HashMap<>();
		Optional<Role> roleOptional = findByRoleId(roleDTO.getRoleId());		
		if(roleOptional.isPresent()) {
			try {
				var role = toRole(roleOptional, roleDTO);
				role.setActive(false);
				save(role);
				return responseUtil.generateResponse(dataMap, ResponseConstants.ROLE_DELETED);
			}catch (Exception e) {
				logger.error(AppConstants.LOG_FORMAT, ResponseConstants.ROLE_NOT_DELETED, e);
				return responseUtil.generateResponse(dataMap, ResponseConstants.ROLE_NOT_DELETED);
			}
		}else {
			return responseUtil.generateResponse(dataMap, ResponseConstants.ROLE_NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<Object> getAllActiveRoles(HttpServletRequest request) {
		Map<String, Object> dataMap = new HashMap<>();
		Set<Role> roles = findByActive(true);
		dataMap.put(DataConstants.ROLE_LIST, toRoleDTOSet(roles));
		return responseUtil.generateResponse(dataMap, ResponseConstants.ROLE_LIST_FOUND);
	}

	@Override
	public ResponseEntity<Object> getRole(HttpServletRequest request, String roleCode) {
		Map<String, Object> dataMap = new HashMap<>();
		Optional<Role> roleOptional = findByRoleIdOrCode(roleCode, roleCode);
		if(roleOptional.isPresent()) {
			dataMap.put(DataConstants.ROLE, toRoleDTO(roleOptional.get()));
			return responseUtil.generateResponse(dataMap, ResponseConstants.ROLE_FOUND);
		}else {
			return responseUtil.generateResponse(dataMap, ResponseConstants.ROLE_NOT_FOUND);
		}
	}

}
