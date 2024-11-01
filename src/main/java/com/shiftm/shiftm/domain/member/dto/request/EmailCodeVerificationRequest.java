package com.shiftm.shiftm.domain.member.dto.request;

public record EmailCodeVerificationRequest(
	String email,
	String verificationCode
) {
}
