package com.musea.security.vk;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

import com.musea.model.domain.vk.VKUser;
import com.musea.service.vk.VKUserService;

/**
 * Authentication provider for VK users.
 * @author Roman Zaripov
 */
public class VKAuthProvider implements AuthenticationProvider {
	
	private static final String VK_VERIFICATION_STR = "%1$s_%2$s_%3$s";
	
	private final String[] secretKeys;
	private final Collection<Long> adminIds;
	
	@Autowired private MessageDigest messageDigest;
	@Autowired private VKUserService userService;
	
	public VKAuthProvider(String[] secretKeys, Long[] adminIds) {
		this.adminIds = Arrays.asList(adminIds);
		this.secretKeys = secretKeys;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		VKAuthToken authToken = (VKAuthToken) authentication;
		
		Long vkUserId = authToken.getVkUserId();
		String hexKey = authToken.getHexKey();
		String applicationId = authToken.getApplicationId();
		
		if (vkUserId == null || hexKey == null || applicationId == null) {
			throw new BadCredentialsException("VK user authentication: bad credentials.");
		}

		for (String secretKey : secretKeys) {
			byte[] authData = messageDigest.digest(String.format(VK_VERIFICATION_STR, applicationId, vkUserId, secretKey).getBytes());
			StringBuilder sb = new StringBuilder();
			for (byte b : authData) {
				String str = Integer.toHexString(0xFF & b);
				if (str.length() == 1) {
					sb.append("0");
				}
				sb.append(str);
			}
			
			if (hexKey.equals(sb.toString())) {
				Collection<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
				authorities.add(VKAuthToken.VK_AUTHORITY);
				if (adminIds.contains(vkUserId)) {
					authorities.add(VKAuthToken.ADMIN_AUTHORITY);
				}
				
				VKUser user = userService.getOrCreateUserBySystemId(vkUserId);
				return new VKAuthToken(user.getId(), vkUserId, authorities);
			}	
		}
		
		throw new BadCredentialsException("VK user authentication: bad credentials.");
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(VKAuthToken.class);
	}
}