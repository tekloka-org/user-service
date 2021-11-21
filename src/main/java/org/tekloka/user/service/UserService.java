package org.tekloka.user.service;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.tekloka.user.document.User;
import org.tekloka.user.dto.LoginDTO;
import org.tekloka.user.dto.SignUpDTO;
import org.tekloka.user.dto.UserDTO;

public interface UserService {

	ResponseEntity<Object> signUp(HttpServletRequest request, SignUpDTO signUpDTO);

	ResponseEntity<Object> login(HttpServletRequest request, LoginDTO loginDTO);

	Optional<User> findByUserId(String userId);

	ResponseEntity<Object> checkServicesStatus();

	ResponseEntity<Object> save(HttpServletRequest request, UserDTO userDTO);

	ResponseEntity<Object> update(HttpServletRequest request, UserDTO userDTO);

	ResponseEntity<Object> delete(HttpServletRequest request, UserDTO userDTO);

	ResponseEntity<Object> getAllActiveUsers(HttpServletRequest request);

	ResponseEntity<Object> getUser(HttpServletRequest request, String userIdentifier);

	ResponseEntity<Object> getLoggedInUser(HttpServletRequest request);	

}
