package org.tekloka.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordDTO {
	
	private String emailAddress;
	private String password;
	private String verificationKey;
	
}
