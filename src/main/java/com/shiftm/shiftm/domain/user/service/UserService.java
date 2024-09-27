package com.shiftm.shiftm.domain.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shiftm.shiftm.domain.user.domain.User;
import com.shiftm.shiftm.domain.user.domain.enums.Role;
import com.shiftm.shiftm.domain.user.dto.request.SignUpRequest;
import com.shiftm.shiftm.domain.user.exception.EmailDuplicateException;
import com.shiftm.shiftm.domain.user.exception.IdDuplicateException;
import com.shiftm.shiftm.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Transactional
	public User signUp(SignUpRequest requestDto) {
		if (isIdDuplicate(requestDto.id())) {
			throw new IdDuplicateException(requestDto.id());
		}

		if (isEmailDuplicate(requestDto.email())) {
			throw new EmailDuplicateException(requestDto.email());
		}

		String password = passwordEncoder.encode(requestDto.password());
		return userRepository.save(requestDto.toEntity(password, Role.USER));
	}

	private boolean isIdDuplicate(String id) {
		return userRepository.existsById(id);
	}

	private boolean isEmailDuplicate(String email) {
		return userRepository.existsByEmail(email);
	}
}
