package com.shiftm.shiftm.global.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.shiftm.shiftm.global.config.filter.JwtAuthFilter;
import com.shiftm.shiftm.global.config.handler.CustomAccessDeniedHandler;
import com.shiftm.shiftm.global.config.handler.CustomAuthenticationEntryPointHandler;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {
	private final JwtAuthFilter jwtAuthFilter;
	private final CustomAuthenticationEntryPointHandler customAuthenticationEntryPointHandler;
	private final CustomAccessDeniedHandler customAccessDeniedHandler;

	private static final String[] whiteList = {
		"/member/signup",
		"/member/check/**",
		"/auth/login",
		"/auth/reissue"
	};

	@Bean
	SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity
			.csrf(AbstractHttpConfigurer::disable)
			.httpBasic(AbstractHttpConfigurer::disable)
			.sessionManagement((session) -> session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.formLogin(AbstractHttpConfigurer::disable)
			.logout(AbstractHttpConfigurer::disable)
			.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
			.exceptionHandling((conf) -> conf
				.authenticationEntryPoint(customAuthenticationEntryPointHandler)
				.accessDeniedHandler(customAccessDeniedHandler)
			)
			.authorizeHttpRequests((authorization) -> authorization
				.anyRequest().authenticated()
			)
			.build();
	}

	@Bean
	public FilterRegistrationBean<JwtAuthFilter> registration(JwtAuthFilter filter) {
		FilterRegistrationBean<JwtAuthFilter> registration = new FilterRegistrationBean<>(filter);
		registration.setEnabled(false);
		return registration;
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return web -> web.ignoring()
			.requestMatchers(whiteList);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
