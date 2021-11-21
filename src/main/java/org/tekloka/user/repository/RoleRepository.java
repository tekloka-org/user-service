package org.tekloka.user.repository;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.tekloka.user.document.Role;

@Repository
public interface RoleRepository extends MongoRepository<Role, String>{
	
	Set<Role> findByActive(boolean active);
	
	Set<Role> findByActiveAndRestricted(boolean active, boolean restricted);		

	Optional<Role> findByRoleIdOrCode(String roleId, String code);
	
	Set<Role> findByRoleIdInAndActive(Set<String> roleIds, boolean active);
	
}

	
	