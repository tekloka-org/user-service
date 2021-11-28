package org.tekloka.user.util;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DataUtil {

	private final Logger logger = LoggerFactory.getLogger(DataUtil.class);
	
	private DataUtil() {
	}
	
	public String getRequestAttributeValue(HttpServletRequest request, String key) {
		var value = "";
		try {
			value = String.valueOf(request.getAttribute(key));
		}catch (Exception e) {
			logger.error("Not able to get String value from request. - {}", e.getMessage());
		}
		return value;
	}
	
	public String getRequestHeaderValue(HttpServletRequest request, String key) {
		return request.getHeader(key);
	}

}
