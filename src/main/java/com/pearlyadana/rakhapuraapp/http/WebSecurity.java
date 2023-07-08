package com.pearlyadana.rakhapuraapp.http;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("WebSecurity")
public class WebSecurity {
	
	@Autowired
	private TokenProvider tokenProvider;
	
	//ref: https://nixmash.com/java/spring-method-expression-based-access-control/
	public boolean checkUserId(final Authentication authentication, String userId) {
		String currentUserId=SecurityUtils.getUserId(authentication);
		if(currentUserId!=null) {
			if(currentUserId.equals(userId)) {
				return true;
			}
		}
		return false;
	}	
	
	public boolean checkPermissions(final Authentication authentication, String features) {
		String currentUserId=SecurityUtils.getUserId(authentication);
		if(currentUserId!=null) {
			if(currentUserId.equals("GET_CUSTOMER_INFO")) {
				return true;
			}
		}
		return false;
	}
	
}
