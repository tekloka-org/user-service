package org.tekloka.user.service.impl;
import java.util.HashMap;
import java.util.HashSet;
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
import org.tekloka.user.constants.RoleConstants;
import org.tekloka.user.document.Role;
import org.tekloka.user.document.User;
import org.tekloka.user.dto.LoginDTO;
import org.tekloka.user.dto.RoleDTO;
import org.tekloka.user.dto.SignUpDTO;
import org.tekloka.user.dto.UserDTO;
import org.tekloka.user.dto.mapper.UserMapper;
import org.tekloka.user.repository.UserRepository;
import org.tekloka.user.security.JWTHelper;
import org.tekloka.user.security.SecurityCache;
import org.tekloka.user.service.AdminService;
import org.tekloka.user.service.RoleService;
import org.tekloka.user.service.UserService;
import org.tekloka.user.util.EncryptDecryptUtil;
import org.tekloka.user.util.ResponseUtil;

@Service
public class UserServiceImpl implements UserService {

	private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	private final UserRepository userRepository;
	private final ResponseUtil responseUtil;
	private final EncryptDecryptUtil encryptDecryptUtil;
	private final UserMapper userMapper;
	private final JWTHelper jwtHelper;
	private final RoleService roleService;
	private final SecurityCache securityCache;
	private final AdminService adminService;
	
	public UserServiceImpl(UserRepository userRepository, ResponseUtil responseUtil,
			EncryptDecryptUtil encryptDecryptUtil, UserMapper userMapper, JWTHelper jwtHelper,
			RoleService roleService, SecurityCache securityCache, AdminService adminService) {
		this.userRepository = userRepository;
		this.responseUtil = responseUtil;
		this.encryptDecryptUtil = encryptDecryptUtil;
		this.userMapper = userMapper;
		this.jwtHelper = jwtHelper;
		this.roleService = roleService;
		this.securityCache = securityCache;
		this.adminService = adminService;
	}
	
	public User toUser(Optional<User> userOptional, UserDTO userDTO) {
		return userMapper.toUser(userOptional, userDTO);
	}
	
	public UserDTO toUserDTO(User user) {
		return userMapper.toUserDTO(user);
	}
	
	public Set<UserDTO> toUserDTOSet(Set<User> users){
		return userMapper.toUserDTOSet(users);
	}
	
	@Override
	public Optional<User> findByUserId(String userId){
		return userRepository.findById(userId);
	}
	
	public Set<User> findByActive(boolean active){
		return userRepository.findByActive(active);
	}
	
	public Optional<User> findByUserIdOrEmailAddress(String userId, String emailAddress){
		return userRepository.findByUserIdOrEmailAddress(userId, emailAddress);
	}
	
	public Optional<User> findByEmailAddressAndActive(String emailAddress, boolean active) {
		return userRepository.findByEmailAddressAndActive(emailAddress, active);
	}
	
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	public ResponseEntity<Object> checkServicesStatus() {
		Map<String, Object> dataMap = new HashMap<>();
		return responseUtil.generateResponse(dataMap, ResponseConstants.USER_SERVICE_ACCESSIBLE);
	}
	
	@Override
	public ResponseEntity<Object> signUp(HttpServletRequest request, SignUpDTO signUpDTO) {
		var dataMap = new HashMap<String, Object>();
		try {
			Optional<User> userOptional = findByEmailAddressAndActive(signUpDTO.getEmailAddress(), true);
			if(userOptional.isPresent()) {
				return responseUtil.generateResponse(dataMap, ResponseConstants.DUPLICATE_EMAIL_ADDRESS);
			}
			var user = userMapper.toUser(signUpDTO);
			Set<RoleDTO> roleSet = new HashSet<>();
			Optional<Role> roleOptional = roleService.findByRoleIdOrCode(null, RoleConstants.AUTHOR);
			if(roleOptional.isPresent()) {
				roleSet.add(roleService.toRoleDTO(roleOptional.get()));
			}
			user.setActive(true);
			save(user);
			return responseUtil.generateResponse(dataMap, ResponseConstants.SIGN_UP_SUCCESS);
		}catch (Exception e) {
			logger.error(AppConstants.LOG_FORMAT, ResponseConstants.SIGN_UP_FAILURE, e);
			return responseUtil.generateResponse(dataMap, ResponseConstants.SIGN_UP_FAILURE);
		}
	}
	
	@Override
	public ResponseEntity<Object> login(HttpServletRequest request, LoginDTO loginDTO) {
		var dataMap = new HashMap<String, Object>();
		try {
			Optional<User> userOptional = findByEmailAddressAndActive(loginDTO.getEmailAddress(), true);
			if(userOptional.isPresent()) {
				var user = userOptional.get();
				if(encryptDecryptUtil.decrypt(user.getPassword()).equals(loginDTO.getPassword())) {
					String authToken = jwtHelper.generateAuthenticationToken(userOptional.get());
					dataMap.put(DataConstants.X_AUTH_TOKEN, authToken);
					dataMap.put(DataConstants.USER, toUserDTO(user));
					return responseUtil.generateResponse(dataMap, ResponseConstants.LOGIN_SUCCESS);
				}
			}
		}catch (Exception e) {
			logger.error(AppConstants.LOG_FORMAT, ResponseConstants.LOGIN_FAILURE, e);
		}
		return responseUtil.generateResponse(dataMap, ResponseConstants.LOGIN_FAILURE);
	}

	
	@Override
	public ResponseEntity<Object> save(HttpServletRequest request, UserDTO userDTO) {
		Map<String, Object> dataMap = new HashMap<>();
		try {
			Optional<User> userOptional = Optional.empty();
			var user = toUser(userOptional, userDTO);
			user.setActive(true);
			user = save(user);
			dataMap.put(DataConstants.USER, toUserDTO(user));
			return responseUtil.generateResponse(dataMap, ResponseConstants.USER_SAVED);
		}catch (Exception e) {
			logger.error(AppConstants.LOG_FORMAT, ResponseConstants.USER_NOT_SAVED, e);
			return responseUtil.generateResponse(dataMap, ResponseConstants.USER_NOT_SAVED);
		}
	}

	@Override
	public ResponseEntity<Object> update(HttpServletRequest request, UserDTO userDTO) {
		Map<String, Object> dataMap = new HashMap<>();
		Optional<User> userOptional = findByUserId(userDTO.getUserId());		
		if(userOptional.isPresent()) {
			try {
				var user = toUser(userOptional, userDTO);
				user = save(user);
				dataMap.put(DataConstants.USER, toUserDTO(user));
				adminService.removeUserFromSecurityCache(user.getUserId());
				return responseUtil.generateResponse(dataMap, ResponseConstants.USER_UPDATED);
			}catch (Exception e) {
				logger.error(AppConstants.LOG_FORMAT, ResponseConstants.USER_NOT_UPDATED, e);
				return responseUtil.generateResponse(dataMap, ResponseConstants.USER_NOT_UPDATED);
			}
		}else {
			return responseUtil.generateResponse(dataMap, ResponseConstants.USER_NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<Object> delete(HttpServletRequest request, UserDTO userDTO) {
		Map<String, Object> dataMap = new HashMap<>();
		Optional<User> userOptional = findByUserId(userDTO.getUserId());		
		if(userOptional.isPresent()) {
			try {
				var user = toUser(userOptional, userDTO);
				user.setActive(false);
				save(user);
				return responseUtil.generateResponse(dataMap, ResponseConstants.USER_DELETED);
			}catch (Exception e) {
				logger.error(AppConstants.LOG_FORMAT, ResponseConstants.USER_NOT_DELETED, e);
				return responseUtil.generateResponse(dataMap, ResponseConstants.USER_NOT_DELETED);
			}
		}else {
			return responseUtil.generateResponse(dataMap, ResponseConstants.USER_NOT_FOUND);
		}	}

	@Override
	public ResponseEntity<Object> getAllActiveUsers(HttpServletRequest request) {
		Map<String, Object> dataMap = new HashMap<>();
		Set<User> users = findByActive(true);
		dataMap.put(DataConstants.USER_LIST, toUserDTOSet(users));
		return responseUtil.generateResponse(dataMap, ResponseConstants.USER_LIST_FOUND);
	}

	@Override
	public ResponseEntity<Object> getUser(HttpServletRequest request, String userIdentifier) {
		Map<String, Object> dataMap = new HashMap<>();
		Optional<User> userOptional = findByUserIdOrEmailAddress(userIdentifier, userIdentifier);
		if(userOptional.isPresent()) {
			dataMap.put(DataConstants.USER, toUserDTO(userOptional.get()));
			return responseUtil.generateResponse(dataMap, ResponseConstants.USER_FOUND);
		}else {
			return responseUtil.generateResponse(dataMap, ResponseConstants.USER_NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<Object> getLoggedInUser(HttpServletRequest request) {
		Map<String, Object> dataMap = new HashMap<>();
		var userId = request.getHeader(DataConstants.LOGGED_IN_USER_ID);
		if(null != userId) {
			Optional<User> userOptional = findByUserId(userId);
			if(userOptional.isPresent()) {
				dataMap.put(DataConstants.USER, toUserDTO(userOptional.get()));
				dataMap.put(DataConstants.ROLE_KEYS, securityCache.getUserRoleSet(userId));
				dataMap.put(DataConstants.PERMISSION_KEYS, securityCache.getUserPermissionSet(userId));
				return responseUtil.generateResponse(dataMap, ResponseConstants.USER_FOUND);	
			}
		}
		return responseUtil.generateResponse(dataMap, ResponseConstants.USER_NOT_FOUND);
	}

	@Override
	public ResponseEntity<Object> getUserAccess(HttpServletRequest request, String userId) {
		return ResponseEntity.ok(securityCache.getUserAccess(userId));
	}

}
