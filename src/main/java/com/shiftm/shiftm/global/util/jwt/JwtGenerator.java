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
	@Value("${jwt.secret}")
	private String secretString;
	private static final String USER_ROLE_CLAIM_NAME = "role";
	private static final long ACCESS_TOKEN_EXPIRATION_TIME = 1000 * 60 * 60 * 24;

	public String generateAccessToken(final String userId, final String role) {
		final long now = getNow();

		return Jwts.builder()
			.subject(userId)
			.claim(USER_ROLE_CLAIM_NAME, role)
			.expiration(getExpiration(now, ACCESS_TOKEN_EXPIRATION_TIME))
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
