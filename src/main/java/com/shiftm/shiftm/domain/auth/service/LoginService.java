package com.shiftm.shiftm.domain.auth.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shiftm.shiftm.domain.user.domain.User;
import com.shiftm.shiftm.domain.auth.dto.request.LoginRequest;
import com.shiftm.shiftm.domain.auth.dto.response.LoginResponse;
import com.shiftm.shiftm.domain.auth.exception.InvalidPasswordException;
import com.shiftm.shiftm.domain.auth.exception.UserNotFoundException;
import com.shiftm.shiftm.domain.user.repository.UserRepository;
import com.shiftm.shiftm.global.util.jwt.JwtGenerator;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LoginService {
	private UserRepository userRepository;
	private RefreshTokenService refreshTokenService;
	private JwtGenerator jwtGenerator;
	private PasswordEncoder passwordEncoder;

	@Transactional
	public LoginResponse login(LoginRequest requestDto) {
		User user = authenticateUser(requestDto.id(), requestDto.password());

		String accessToken = jwtGenerator.generateAccessToken(user.getId(), user.getRole().name());
		String refreshToken = jwtGenerator.generateRefreshToken(user.getId());

		refreshTokenService.saveRefreshToken(user.getId(), refreshToken);

		return new LoginResponse(accessToken, refreshToken);
	}

	private User authenticateUser(String id, String password) {
		Optional<User> optionalUser = userRepository.findById(id);

		if (!existUser(optionalUser)) {
			throw new UserNotFoundException(id);
		}

		User user = optionalUser.get();

		if (!isEqual(user.getPassword(), passwordEncoder.encode(password))) {
			throw new InvalidPasswordException();
		}

		return user;
	}

	private boolean existUser(Optional<User> optionalUser) {
		return optionalUser.isPresent();
	}

	private boolean isEqual(String password, String reqPassword) {
		return password.equals(reqPassword);
	}
}
