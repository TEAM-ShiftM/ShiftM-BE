package com.shiftm.shiftm.domain.auth.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shiftm.shiftm.domain.auth.dto.request.LoginRequest;
import com.shiftm.shiftm.domain.auth.dto.response.TokenResponse;
import com.shiftm.shiftm.domain.auth.service.LoginService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class LoginController {
	private LoginService loginService;

	@PostMapping("/login")
	public TokenResponse login(@RequestBody LoginRequest requestDto) {
		TokenResponse loginResponse = loginService.login(requestDto);
		return loginResponse;
	}

	@PostMapping("/reissue")
	public TokenResponse reissue(@RequestHeader("Authorization") String refreshToken) {
		TokenResponse reissueResponse = loginService.reissue(refreshToken);
		return reissueResponse;
	}
}
