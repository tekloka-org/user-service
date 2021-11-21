package org.tekloka.user.document;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "roles")
public class Role extends AuditMetadata {

	@Id
	private String roleId;	
	private String code;
	private String name;
	private boolean restricted;
	
	@DBRef(lazy = true)
	private Set<Permission> permissions;
	
	private boolean active;
	
}