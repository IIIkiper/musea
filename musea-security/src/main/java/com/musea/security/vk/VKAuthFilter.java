package com.musea.security.vk;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AuthenticationManager;

import com.musea.security.AuthFilter;

public class VKAuthFilter extends AuthFilter<VKAuthToken> {

	public VKAuthFilter(AuthenticationManager manager) {
		super(manager);
	}

	@Override
	protected VKAuthToken getAuthToken(HttpServletRequest request) {
		Long vkUserId;
		try {
			vkUserId = new Long(request.getParameter("viewer_id"));
		} catch (Exception ex) {
			return null;
		}
		String hexKey = request.getParameter("auth_key");
		String applicationId = request.getParameter("api_id");
		
		if (vkUserId != null && hexKey != null && applicationId != null) {
			return new VKAuthToken(vkUserId, applicationId, hexKey);
		}
		
		return null;
	}
}