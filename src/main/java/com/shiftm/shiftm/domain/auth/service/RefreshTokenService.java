package com.shiftm.shiftm.domain.auth.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shiftm.shiftm.domain.auth.domain.RefreshToken;
import com.shiftm.shiftm.domain.auth.repository.RefreshTokenRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {
	private RefreshTokenRepository refreshTokenRepository;

	@Transactional
	public void saveRefreshToken(String id, String refreshToken) {
		RefreshToken token = RefreshToken.builder()
			.id(id)
			.refreshToken(refreshToken)
			.build();

		refreshTokenRepository.save(token);
	}
}
