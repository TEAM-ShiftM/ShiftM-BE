package com.shiftm.shiftm.global.auth.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shiftm.shiftm.global.error.exception.ErrorCode;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationEntryPointHandler implements AuthenticationEntryPoint {
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
			throws IOException {

		response.setStatus(ErrorCode.UNAUTHORIZED.getStatus());
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=UTF-8");

		Map<String, Object> jsonResponse = new HashMap<>();
		jsonResponse.put("code", ErrorCode.UNAUTHORIZED.getCode());
		jsonResponse.put("message", ErrorCode.UNAUTHORIZED.getMessage());

		ObjectMapper objectMapper = new ObjectMapper();
		response.getWriter().write(objectMapper.writeValueAsString(jsonResponse));
		response.getWriter().flush();
	}
}
