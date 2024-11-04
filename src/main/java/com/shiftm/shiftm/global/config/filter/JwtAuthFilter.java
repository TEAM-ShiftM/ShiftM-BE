package com.shiftm.shiftm.global.config.filter;

import java.io.IOException;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.shiftm.shiftm.domain.member.dao.MemberFinder;
import com.shiftm.shiftm.domain.member.domain.Member;
import com.shiftm.shiftm.global.util.jwt.JwtValidator;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtAuthFilter extends OncePerRequestFilter {
	private final MemberFinder memberFinder;
	private final JwtValidator jwtValidator;

	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {

		final String accessToken = getAccessToken(request);

		authenticate(accessToken);

		filterChain.doFilter(request, response);
	}

	private String getAccessToken(HttpServletRequest request) {
		final String accessToken = request.getHeader("Authorization");

		jwtValidator.validateToken(accessToken);

		return accessToken;
	}

	private void authenticate(String accessToken) {
		String userId = jwtValidator.getSubject(accessToken);

		if (SecurityContextHolder.getContext().getAuthentication() == null) {
			SecurityContextHolder.getContext().setAuthentication(getUserAuth(userId));
		}
	}

	private UsernamePasswordAuthenticationToken getUserAuth(String userId) {
		Member userInfo = memberFinder.getUser(userId);

		return new UsernamePasswordAuthenticationToken(userInfo.getId(),
			null,
			Collections.singleton(new SimpleGrantedAuthority(userInfo.getRole().name())));
	}
}
