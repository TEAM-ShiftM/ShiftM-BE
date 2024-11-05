package com.shiftm.shiftm.global.auth.annotation;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.shiftm.shiftm.global.error.exception.ErrorCode;

@Component
public class AuthIdResolver implements HandlerMethodArgumentResolver {
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(AuthId.class)
			&& parameter.getParameterType().equals(String.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

		var authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || authentication.getPrincipal() == null) {
			throw new IllegalStateException(ErrorCode.UNAUTHORIZED.getMessage());
		}

		return authentication.getPrincipal().toString();
	}
}
