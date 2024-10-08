package com.shiftm.shiftm.domain.user.dto.response;

public record LoginResponse(
	String accessToken,
	String refreshToken
) {
}
