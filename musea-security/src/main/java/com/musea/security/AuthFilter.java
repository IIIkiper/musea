package com.musea.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

public abstract class AuthFilter<T extends AuthToken> extends GenericFilterBean {
	
	private final AuthenticationManager manager;
	
	public AuthFilter(AuthenticationManager manager) {
		this.manager = manager;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		
		T authToken = getAuthToken(httpRequest);
		
		if (authToken == null) { // Processing next filter
			chain.doFilter(request, response);
			return;			
		}
		
		if (isAuthenticationRequired(authToken)) {
			httpRequest.getSession().invalidate();
			try {			
				Authentication authResult = manager.authenticate(authToken);
				SecurityContextHolder.getContext().setAuthentication(authResult);
			} catch (AuthenticationException ex) {
				SecurityContextHolder.clearContext();
			}			
		}
		
		chain.doFilter(request, response);
	}
	
	protected abstract T getAuthToken(HttpServletRequest httpRequest);
	
	private boolean isAuthenticationRequired(T authToken) {
		Authentication existingAuth = SecurityContextHolder.getContext().getAuthentication();		
		return existingAuth == null || !existingAuth.isAuthenticated() || !authToken.equals(existingAuth);
	}
}