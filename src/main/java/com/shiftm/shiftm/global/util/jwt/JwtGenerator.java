package com.shiftm.shiftm.global.util.jwt;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtGenerator {
	@Value("${jwt.secret.key}")
	private String secretString;

	@Value("${jwt.access.token.expiration.time}")
	private long accessTokenExpirationTime;

	@Value("${jwt.refresh.token.expiration.time}")
	private long refreshTokenExpirationTime;

	private static final String USER_ROLE_CLAIM_NAME = "role";

	public String generateAccessToken(final String userId, final String role) {
		final long now = getNow();

		return Jwts.builder()
			.subject(userId)
			.claim(USER_ROLE_CLAIM_NAME, role)
			.expiration(getExpiration(now, accessTokenExpirationTime))
			.signWith(getSigningKey(secretString))
			.compact();
	}

	public String generateRefreshToken(final String userId) {
		final long now = getNow();

		return Jwts.builder()
			.subject(userId)
			.expiration(getExpiration(now, refreshTokenExpirationTime))
			.signWith(getSigningKey(secretString))
			.compact();
	}

	private long getNow() {
		return System.currentTimeMillis();
	}

	private Date getExpiration(final long now, final long expirationTime) {
		return new Date(now + expirationTime);
	}

	private SecretKey getSigningKey(final String secretString) {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretString));
	}
}
