package com.shiftm.shiftm.domain.auth.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shiftm.shiftm.domain.auth.dto.request.LoginRequest;
import com.shiftm.shiftm.domain.auth.dto.response.TokenResponse;
import com.shiftm.shiftm.domain.auth.service.LoginService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class LoginController {
	private final LoginService loginService;

	@PostMapping("/login")
	public TokenResponse login(@Valid @RequestBody final LoginRequest requestDto) {
		return loginService.login(requestDto);
	}

	@PostMapping("/reissue")
	public TokenResponse reissue(@RequestHeader("Authorization") final String refreshToken) {
		return loginService.reissue(refreshToken);
	}
}
