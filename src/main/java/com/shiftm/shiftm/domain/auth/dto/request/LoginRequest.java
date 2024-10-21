package com.shiftm.shiftm.domain.auth.dto.request;

public record LoginRequest(
	String id,
	String password
) {
}
