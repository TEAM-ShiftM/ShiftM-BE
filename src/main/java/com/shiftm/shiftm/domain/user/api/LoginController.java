package com.shiftm.shiftm.domain.user.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shiftm.shiftm.domain.user.dto.request.LoginRequest;
import com.shiftm.shiftm.domain.user.dto.response.LoginResponse;
import com.shiftm.shiftm.domain.user.service.LoginService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class LoginController {
	private LoginService loginService;

	@PostMapping("/login")
	public LoginResponse login(@RequestBody LoginRequest requestDto) {
		LoginResponse loginResponse = loginService.login(requestDto);
		return loginResponse;
	}
}
