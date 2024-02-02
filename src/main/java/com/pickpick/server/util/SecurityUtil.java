package com.pickpick.server.util;

import com.pickpick.server.security.domain.UserDetailsImpl;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
	public static String getLoginEmail(){
		UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return user.getUsers().getEmail();
	}
}