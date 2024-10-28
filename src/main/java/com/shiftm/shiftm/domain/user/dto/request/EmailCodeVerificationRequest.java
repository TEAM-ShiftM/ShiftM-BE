package com.shiftm.shiftm.domain.user.dto.request;

public record EmailCodeVerificationRequest(
	String email,
	String verificationCode
) {
}
