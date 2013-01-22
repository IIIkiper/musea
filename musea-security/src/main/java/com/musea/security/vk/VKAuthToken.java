package com.musea.security.vk;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.musea.security.AuthToken;

public class VKAuthToken extends AuthToken {
	private static final long serialVersionUID = 2266000665986823241L;
	
	public static final GrantedAuthority VK_AUTHORITY = new SimpleGrantedAuthority("ROLE_VK");
	
	private final Long vkUserId;
	private final Long userId;
	
	private final String hexKey;
	private final String applicationId;

	public VKAuthToken(Long vkUserId, String applicationId, String hexKey) {
		super(null);
		this.vkUserId = vkUserId;
		this.applicationId = applicationId;
		this.hexKey = hexKey;
		this.userId = null;
	}
	
	public VKAuthToken(Long userId, Long vkUserId, Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.vkUserId = vkUserId;
		this.userId = userId;
		this.hexKey = null;
		this.applicationId = null;
	}

	@Override
	public Long getPrincipal() {
		return userId;
	}
	
	@Override
	public boolean equals(Object obj) {
		try {
			return obj != null && ((VKAuthToken) obj).vkUserId == this.vkUserId;
		} catch (Exception ex) {
			return false;
		}
	}
	
	// --- Getters ---
	public String getHexKey() {
		return hexKey;
	}
	public String getApplicationId() {
		return applicationId;
	}
	public Long getVkUserId() {
		return vkUserId;
	}
}