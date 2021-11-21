package org.tekloka.user.repository;

import java.util.Set;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.tekloka.user.document.Permission;

@Repository
public interface PermissionRepository extends MongoRepository<Permission, String>{

	Set<Permission> findByActive(boolean active);

	Set<Permission> findByActiveAndRestricted(boolean active, boolean restricted);
	
	Set<Permission> findByPermissionIdInAndActive(Set<String> permissionIds, boolean active);
	
}

	
	