package org.tekloka.user.document;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Document(collection = "users")
public class User extends AuditMetadata {

	@Id
	private String userId;
	
	private String name;
	private String emailAddress;
	private String password;
	
	@DBRef(lazy = true)
	private Set<Role> roles;
	
	private boolean active;
	
	private boolean verified;
	private String verificationKey;
	
}