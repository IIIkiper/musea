package com.musea.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.web.filter.GenericFilterBean;

import com.musea.security.vk.VKAuthFilter;
import com.musea.security.vk.VKAuthProvider;

@Configuration
public class SpringSecurity {
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Bean
	public GenericFilterBean vkAuthFilter() {
		return new VKAuthFilter(authManager);
	}

	@Bean
	public AuthenticationProvider vkAuthProvider() {
		return new VKAuthProvider(null, null);
	}
	
	@Bean
	public AuthenticationEntryPoint entryPoint403() {
		return new Http403ForbiddenEntryPoint();
	}
}