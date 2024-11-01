package com.shiftm.shiftm.domain.member.dto.request;

public record FindPasswordRequest(
	String id,
	String email
) {
}
