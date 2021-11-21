package org.tekloka.user.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "permissions")
public class Permission extends AuditMetadata {

	@Id
	private String permissionId;
	private String code;
	private String name;
	private boolean restricted;
	private boolean active;

}
