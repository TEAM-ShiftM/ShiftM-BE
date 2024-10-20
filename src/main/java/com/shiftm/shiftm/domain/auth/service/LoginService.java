package com.shiftm.shiftm.domain.auth.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shiftm.shiftm.domain.auth.domain.RefreshToken;
import com.shiftm.shiftm.domain.auth.exception.InvalidRefreshTokenException;
import com.shiftm.shiftm.domain.user.domain.User;
import com.shiftm.shiftm.domain.auth.dto.request.LoginRequest;
import com.shiftm.shiftm.domain.auth.dto.response.TokenResponse;
import com.shiftm.shiftm.domain.auth.exception.InvalidPasswordException;
import com.shiftm.shiftm.domain.auth.exception.UserNotFoundException;
import com.shiftm.shiftm.domain.user.repository.UserRepository;
import com.shiftm.shiftm.global.util.jwt.JwtGenerator;
import com.shiftm.shiftm.global.util.jwt.JwtValidator;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LoginService {
	private final UserRepository userRepository;
	private final RefreshTokenService refreshTokenService;
	private final JwtGenerator jwtGenerator;
	private final JwtValidator jwtValidator;
	private final PasswordEncoder passwordEncoder;

	@Transactional
	public TokenResponse login(LoginRequest requestDto) {
		authenticateUser(requestDto.id(), requestDto.password());

		TokenResponse token = generateToken(requestDto.id());

		refreshTokenService.saveRefreshToken(requestDto.id(), token.refreshToken());

		return token;
	}

	@Transactional
	public TokenResponse reissue(String refreshToken) {
		validateRefreshToken(refreshToken);

		String userId = jwtValidator.getSubject(refreshToken);

		TokenResponse token = generateToken(userId);

		refreshTokenService.saveRefreshToken(userId, token.refreshToken());

		return token;
	}

	private void authenticateUser(String id, String password) {
		User user = getUser(id);

		if (!isEqual(user.getPassword(), passwordEncoder.encode(password))) {
			throw new InvalidPasswordException();
		}
	}

	private void validateRefreshToken(String refreshToken) {
		jwtValidator.validateRefreshToken(refreshToken);

		String userId = jwtValidator.getSubject(refreshToken);

		RefreshToken storedRefreshToken = refreshTokenService.getRefreshToken(userId);

		if (!isEqual(refreshToken, storedRefreshToken.getRefreshToken())) {
			throw new InvalidRefreshTokenException();
		}
	}

	private TokenResponse generateToken(String userId) {
		User user = getUser(userId);

		String accessToken = jwtGenerator.generateAccessToken(user.getId(), user.getRole().name());
		String refreshToken = jwtGenerator.generateRefreshToken(user.getId());

		return new TokenResponse(accessToken, refreshToken);
	}

	private User getUser(String userId) {
		Optional<User> optionalUser = userRepository.findById(userId);

		if (optionalUser.isEmpty()) {
			throw new UserNotFoundException(userId);
		}

		return optionalUser.get();
	}

	private boolean isEqual(String str1, String str2) {
		return str1.equals(str2);
	}
}
