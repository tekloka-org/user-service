package org.tekloka.user.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.tekloka.user.constants.DataConstants;
import org.tekloka.user.document.User;
import org.tekloka.user.service.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTHelper {
	
	@Value("${jwt.secret.key}") 
	private String jwtSecretKey;
	
	private final UserService userService;
	
	public JWTHelper(@Lazy UserService userService) {
		this.userService = userService;
	}
	
	public String generateAuthenticationToken(User user) {
		Long now = System.currentTimeMillis();
		return Jwts.builder()
			.setSubject(user.getEmailAddress())
			.addClaims(generateCustomClaims(user))
			.setIssuedAt(new Date(now))
			.setExpiration(new Date(now + (24 * 60 * 60 * 1000)))  //1 day
			.signWith(SignatureAlgorithm.HS512, jwtSecretKey.getBytes())
			.compact();
	}
	
	public Claims decodeToken(String authToken){
		Claims claims = null;
		if (authToken != null) {
			claims = Jwts.parser().setSigningKey(jwtSecretKey.getBytes()).parseClaimsJws(authToken)
					.getBody();
		}
		return claims;
	}

	public String generateRefreshToken(Claims claims) {
		if(null != claims.get(DataConstants.LOGGED_IN_USER_ID)) {
			Optional<User> userOptional = userService.findByUserId(claims.get(DataConstants.LOGGED_IN_USER_ID).toString());
			if(userOptional.isPresent()) {
				return generateAuthenticationToken(userOptional.get());
			}
		}
		return null;
	}
	
	private Map<String, Object> generateCustomClaims(User user) {
		Map<String, Object> customClaims = new HashMap<>();
		customClaims.put(DataConstants.LOGGED_IN_USER_ID, user.getUserId());
		customClaims.put(DataConstants.EMAIL_ADDRESS, user.getEmailAddress());
		customClaims.put(DataConstants.USER_NAME, user.getName());
		return customClaims;
	}
}
