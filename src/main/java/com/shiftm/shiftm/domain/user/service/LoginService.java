package com.shiftm.shiftm.domain.user.service;

import org.springframework.stereotype.Service;

import com.shiftm.shiftm.domain.user.dto.request.LoginRequest;
import com.shiftm.shiftm.domain.user.dto.response.LoginResponse;
import com.shiftm.shiftm.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LoginService {
	private UserRepository userRepository;

	public LoginResponse login(LoginRequest requestDto) {

	}
}
