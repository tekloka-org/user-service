package org.tekloka.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpDTO {
	
	private String name;
	private String emailAddress;
	private String mobileNumber;
	private String password;	
	
}
