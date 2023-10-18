package com.dkhang.shopapplication.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfiguration {
	
	private final AuthenticationProvider authenticationProvider;
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final LogoutHandler logoutHandler;
	@Value("${api.prefix}")
	private String apiPrefix;
		
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests( request -> request.requestMatchers(String.format("%s/users/auth/*", apiPrefix))
													.permitAll()
													.requestMatchers(HttpMethod.PUT,String.format("%s/orders/**", apiPrefix))
													.hasRole("ADMIN")
													.anyRequest()
													.authenticated()
								)
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authenticationProvider(authenticationProvider)
			.addFilterBefore(jwtAuthenticationFilter,UsernamePasswordAuthenticationFilter.class)
			.logout( logout -> logout.logoutUrl(String.format("%s/users/auth/logout", apiPrefix))
									.permitAll()
									.addLogoutHandler(logoutHandler)
									.logoutSuccessHandler((request,response,authentication) -> SecurityContextHolder.clearContext())
					);
		return http.build();
	}
}
