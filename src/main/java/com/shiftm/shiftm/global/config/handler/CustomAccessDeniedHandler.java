package com.shiftm.shiftm.global.config.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shiftm.shiftm.global.error.exception.ErrorCode;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception)
			throws IOException {

		response.setStatus(ErrorCode.FORBIDDEN.getStatus());
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=UTF-8");

		Map<String, Object> jsonResponse = new HashMap<>();
		jsonResponse.put("code", ErrorCode.FORBIDDEN.getCode());
		jsonResponse.put("message", ErrorCode.FORBIDDEN.getMessage());

		ObjectMapper objectMapper = new ObjectMapper();
		response.getWriter().write(objectMapper.writeValueAsString(jsonResponse));
		response.getWriter().flush();
	}
}
