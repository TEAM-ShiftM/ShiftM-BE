package com.shiftm.shiftm.domain.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shiftm.shiftm.domain.user.domain.User;
import com.shiftm.shiftm.domain.user.domain.enums.Role;
import com.shiftm.shiftm.domain.user.dto.request.SignUpRequest;
import com.shiftm.shiftm.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Transactional
	public User signUp(SignUpRequest requestDto) {
		String password = passwordEncoder.encode(requestDto.password());
		return userRepository.save(requestDto.toEntity(password, Role.USER));
	}
}
