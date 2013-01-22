package com.musea.security;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public abstract class AuthToken extends AbstractAuthenticationToken {
	private static final long serialVersionUID = 4636520683616872348L;
	
	public static final GrantedAuthority ADMIN_AUTHORITY = new SimpleGrantedAuthority("ROLE_ADMIN");

	public AuthToken(Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		super.setAuthenticated(authorities != null);
	}

	/**
	 * @return user ID
	 */
	@Override
	public abstract Long getPrincipal();
	
	/**
	 * Credentials does not needed for application. Override if required.
	 */
	@Override
	public Object getCredentials() {
		return null;
	}
	
	@Override
	public void setAuthenticated(boolean authenticated) {
		throw new UnsupportedOperationException();
	}
}