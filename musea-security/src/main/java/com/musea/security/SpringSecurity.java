package com.musea.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.web.filter.GenericFilterBean;

import com.musea.security.vk.VKAuthFilter;
import com.musea.security.vk.VKAuthProvider;

@Configuration
@PropertySource("classpath:com/musea/security/security.properties")
@ImportResource("classpath:com/musea/security/security.xml")
public class SpringSecurity {
	
	@Value("${security.vk.app.keys}")
	private String[] applicationKeys;
	
	@Value("${security.vk.admin.ids}")
	private Long[] adminIds;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Bean
	public GenericFilterBean vkAuthFilter() {
		return new VKAuthFilter(authManager);
	}

	@Bean
	public AuthenticationProvider vkAuthProvider() {
		return new VKAuthProvider(applicationKeys, adminIds);
	}
	
	@Bean
	public AuthenticationEntryPoint entryPoint403() {
		return new Http403ForbiddenEntryPoint();
	}
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
	    return new PropertySourcesPlaceholderConfigurer();
	}
}