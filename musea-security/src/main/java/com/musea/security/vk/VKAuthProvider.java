package com.musea.security.vk;

import java.security.MessageDigest;
import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;


/**
 * Authentication provider for VK users.
 * @author Roman Zaripov
 */
public class VKAuthProvider implements AuthenticationProvider {
	
	private static final String VK_VERIFICATION_STR = "%1$s_%2$s_%3$s";
	
	private final Collection<String> secretKeys;
	private final Collection<Integer> adminIds;
	
	@Autowired private MessageDigest messageDigest;
	
	public VKAuthProvider(Collection<String> secretKeys, Collection<Integer> adminIds) {
		this.adminIds = adminIds;
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
				
				//VKUser user = userService.getUserByVkUserId(userID);
				return new VKAuthToken(0l, vkUserId, authorities); // TODO
			}	
		}
		
		throw new BadCredentialsException("VK user authentication: bad credentials.");
		//return null; // Trying next provider
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(VKAuthToken.class);
	}
}