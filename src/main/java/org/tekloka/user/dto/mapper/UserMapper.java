package org.tekloka.user.dto.mapper;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.tekloka.user.document.Role;
import org.tekloka.user.document.User;
import org.tekloka.user.dto.RoleDTO;
import org.tekloka.user.dto.SignUpDTO;
import org.tekloka.user.dto.UserDTO;
import org.tekloka.user.service.RoleService;
import org.tekloka.user.util.EncryptDecryptUtil;

@Component
public class UserMapper {
	
	private final EncryptDecryptUtil encryptDecryptUtil;
	private final RoleService roleService;
	
	public UserMapper(EncryptDecryptUtil encryptDecryptUtil, @Lazy RoleService roleService) {
		this.encryptDecryptUtil = encryptDecryptUtil;
		this.roleService = roleService;
	}
	
	public User toUser(Optional<User> userOptional, UserDTO userDTO) {
		var user = new User();
		if(userOptional.isPresent()) {
			user = userOptional.get();
		}
		user.setName(userDTO.getName());
		user.setEmailAddress(userDTO.getEmailAddress());
		user.setVerified(userDTO.isVerified());
		if(null != userDTO.getPassword() && !userDTO.getPassword().isEmpty()) {
			user.setPassword(encryptDecryptUtil.encrypt(userDTO.getPassword()));
		}

		Set<String> roleIds = new HashSet<>();
		if(null != userDTO.getRoles()) {
			roleIds = userDTO.getRoles().stream().map(RoleDTO::getRoleId).collect(Collectors.toSet());
		}
		Set<Role> roles = roleService.findByRoleIdInAndActive(roleIds, true);
		if(null != roles) {
			user.setRoles(roles);
		}
		
		return user;
	}
	
	public UserDTO toUserDTO(User user) {
		var userDTO = new UserDTO();
		userDTO.setUserId(user.getUserId());
		userDTO.setName(user.getName());
		userDTO.setEmailAddress(user.getEmailAddress());
		userDTO.setVerified(user.isVerified());
		Set<RoleDTO> roleDTOs = roleService.toRoleDTOSet(user.getRoles());
		if(null != roleDTOs) {
			userDTO.setRoles(roleDTOs);
		}
		return userDTO;
	}
	
	public Set<UserDTO> toUserDTOSet(Set<User> users){
		return users.stream().map(this::toUserDTO).collect(Collectors.toSet());
	}
	
	public User toUser(SignUpDTO signUpDTO) {
		var user = new User();
		user.setName(signUpDTO.getName());
		user.setEmailAddress(signUpDTO.getEmailAddress());
		user.setPassword(encryptDecryptUtil.encrypt(signUpDTO.getPassword()));
		return user;
	}
	
}
