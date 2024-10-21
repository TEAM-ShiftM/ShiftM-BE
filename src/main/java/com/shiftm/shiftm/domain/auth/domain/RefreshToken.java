package com.shiftm.shiftm.domain.auth.domain;

import org.springframework.data.redis.core.RedisHash;

import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "refreshToken", timeToLive = 60 * 60 * 24 * 14)
public class RefreshToken {
	@Id
	private String id;

	private String refreshToken;

	@Builder
	public RefreshToken(String id, String refreshToken) {
		this.id = id;
		this.refreshToken = refreshToken;
	}
}
