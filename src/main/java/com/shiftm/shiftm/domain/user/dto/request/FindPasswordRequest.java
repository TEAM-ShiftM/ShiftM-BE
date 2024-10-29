package com.shiftm.shiftm.domain.user.dto.request;

public record FindPasswordRequest(
	String id,
	String email
) {
}
