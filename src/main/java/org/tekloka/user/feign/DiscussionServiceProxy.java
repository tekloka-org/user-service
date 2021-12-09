package org.tekloka.user.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.tekloka.user.constants.DataConstants;

@FeignClient(name = "discussion-service")
public interface DiscussionServiceProxy {

	@GetMapping(path = "/admin/remove-user-from-security-cache/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> removeUserFromSecurityCache(@RequestHeader(value = DataConstants.FEIGN_CLIENT_ACCESS_ID, required = true) String feignClientAccessId,
			@PathVariable("userId") String userId);
	
	@GetMapping(path = "/admin/clear-security-cache", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> clearSecurityCache(@RequestHeader(value = DataConstants.FEIGN_CLIENT_ACCESS_ID, required = true) String feignClientAccessId);

}
