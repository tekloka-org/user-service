package org.tekloka.user.util;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.tekloka.user.constants.DataConstants;

public class AuditorAwareUtil implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
		var userId= "";
		var servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();		
		if(null != servletRequestAttributes) {
			var request = servletRequestAttributes.getRequest();
			userId = request.getHeader(DataConstants.LOGGED_IN_USER_ID);
		}
		return Optional.of(userId);
	}

}