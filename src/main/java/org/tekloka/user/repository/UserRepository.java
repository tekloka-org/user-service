package org.tekloka.user.repository;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.tekloka.user.document.User;

@Repository
public interface UserRepository extends MongoRepository<User, String>{
	
	Optional<User> findByEmailAddressAndActive(String emailAddress, boolean active);
	
	Set<User> findByActive(boolean active);
	
	Optional<User> findByUserIdOrEmailAddress(String userId, String emailAddress);
	
	
}

	
	