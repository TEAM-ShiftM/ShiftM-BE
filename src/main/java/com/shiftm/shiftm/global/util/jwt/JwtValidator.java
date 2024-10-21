package com.shiftm.shiftm.global.util.jwt;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.shiftm.shiftm.domain.auth.exception.InvalidBearerPrefixException;
import com.shiftm.shiftm.domain.auth.exception.InvalidTokenException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtValidator {
	@Value("${jwt.secret.key}")
	private String secretString;

	private static final String BEARER = "Bearer ";

	public void validateRefreshToken(final String refreshToken) {
		try {
			Claims claims = parseToken(refreshToken);
		} catch (JwtException e) {
			throw new InvalidTokenException();
		}
	}

	public boolean isRefreshTokenEqual(final String refreshToken, final String storedRefreshToken) {
		return getToken(refreshToken).equals(storedRefreshToken);
	}

	public String getSubject(final String refreshToken) {
		return parseToken(refreshToken).getSubject();
	}

	private Claims parseToken(final String token) {
		return Jwts.parser()
			.verifyWith(getSigningKey(secretString))
			.build()
			.parseSignedClaims(getToken(token))
			.getPayload();
	}

	private String getToken(final String token) {
		if (!token.startsWith(BEARER)) {
			throw new InvalidBearerPrefixException();
		}

		return token.substring(BEARER.length());
	}

	private SecretKey getSigningKey(final String secretString) {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretString));
	}
}
