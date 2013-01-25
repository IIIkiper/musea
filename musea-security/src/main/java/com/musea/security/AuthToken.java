package com.musea.security;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public abstract class AuthToken extends AbstractAuthenticationToken {
	private static final long serialVersionUID = 4636520683616872348L;
	
	public static final GrantedAuthority ADMIN_AUTHORITY = new SimpleGrantedAuthority("ROLE_ADMIN");
	private final Long userId;

	public AuthToken(Collection<? extends GrantedAuthority> authorities) {
		this(null, authorities);
	}
	
	public AuthToken(Long userId, Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		super.setAuthenticated(authorities != null && !authorities.isEmpty());
		this.userId = userId;
	}

	/**
	 * @return user ID
	 */
	@Override
	public final Long getPrincipal() {
		return userId;
	}
	
	@Override
	public Object getCredentials() {
		return null; // Credentials does not needed for application. Override if required.
	}
	
	@Override
	public void setAuthenticated(boolean authenticated) {
		throw new UnsupportedOperationException();
	}
}