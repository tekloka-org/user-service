package org.tekloka.user.aop;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.tekloka.user.constants.DataConstants;
import org.tekloka.user.constants.RoleConstants;
import org.tekloka.user.exception.UnauthorizedException;
import org.tekloka.user.security.AccessPermissions;
import org.tekloka.user.security.SecurityCache;
import org.tekloka.user.util.DataUtil;

@Aspect
@Component
public class AccessPermissionsAspect {
	
	private final SecurityCache securityCache;
	private final DataUtil dataUtil;
	
	public AccessPermissionsAspect(SecurityCache securityCache, DataUtil dataUtil) {
		this.securityCache = securityCache;
		this.dataUtil = dataUtil;
	}

	@Before("@annotation(org.tekloka.user.security.AccessPermissions)")
	public void verifyAccessPermissions(JoinPoint joinPoint){
		var hasAccess = false;
		String[] accessPermissions = ((MethodSignature) joinPoint.getSignature()).getMethod()
				.getAnnotation(AccessPermissions.class).value();
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				 .getRequest();
		Set<String> userRolSet = securityCache.getUserRoleSet(dataUtil.getStringValueFromRequest(request, DataConstants.LOGGED_IN_USER_ID));
		Set<String> userPermissionSet = securityCache.getUserPermissionSet(dataUtil.getStringValueFromRequest(request, DataConstants.LOGGED_IN_USER_ID));
		for(String permission: accessPermissions) {
			if(userRolSet.contains(RoleConstants.SUPER_ADMIN) || userPermissionSet.contains(permission)) {
				hasAccess = true;
				break;
			}
		}
		if(!hasAccess) {
			 throw new UnauthorizedException();
		}
	}
	
}