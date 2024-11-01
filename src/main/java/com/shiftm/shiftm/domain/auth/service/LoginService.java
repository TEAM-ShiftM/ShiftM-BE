package com.shiftm.shiftm.domain.auth.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shiftm.shiftm.domain.auth.domain.RefreshToken;
import com.shiftm.shiftm.domain.auth.exception.InvalidRefreshTokenException;
import com.shiftm.shiftm.domain.member.domain.Member;
import com.shiftm.shiftm.domain.auth.dto.request.LoginRequest;
import com.shiftm.shiftm.domain.auth.dto.response.TokenResponse;
import com.shiftm.shiftm.domain.auth.exception.InvalidPasswordException;
import com.shiftm.shiftm.domain.auth.exception.UserNotFoundException;
import com.shiftm.shiftm.domain.member.dao.MemberRepository;
import com.shiftm.shiftm.global.util.jwt.JwtGenerator;
import com.shiftm.shiftm.global.util.jwt.JwtValidator;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LoginService {
	private final MemberRepository userRepository;
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
		Member user = getUser(id);

		if (!isMatch(password, user.getPassword())) {
			throw new InvalidPasswordException();
		}
	}

	private void validateRefreshToken(String refreshToken) {
		jwtValidator.validateRefreshToken(refreshToken);

		String userId = jwtValidator.getSubject(refreshToken);

		RefreshToken storedRefreshToken = refreshTokenService.getRefreshToken(userId);

		if (!jwtValidator.isRefreshTokenEqual(refreshToken, storedRefreshToken.getRefreshToken())) {
			throw new InvalidRefreshTokenException();
		}
	}

	private TokenResponse generateToken(String userId) {
		Member user = getUser(userId);

		String accessToken = jwtGenerator.generateAccessToken(user.getId(), user.getRole().name());
		String refreshToken = jwtGenerator.generateRefreshToken(user.getId());

		return new TokenResponse(accessToken, refreshToken);
	}

	private Member getUser(String userId) {
		Optional<Member> optionalUser = userRepository.findById(userId);

		if (optionalUser.isEmpty()) {
			throw new UserNotFoundException(userId);
		}

		return optionalUser.get();
	}

	private boolean isMatch(String password, String storedPassword) {
		return passwordEncoder.matches(password, storedPassword);
	}
}
