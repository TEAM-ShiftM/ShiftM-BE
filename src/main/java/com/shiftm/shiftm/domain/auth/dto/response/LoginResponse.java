package com.shiftm.shiftm.domain.auth.dto.response;

public record LoginResponse(
	String accessToken,
	String refreshToken
) {
}
