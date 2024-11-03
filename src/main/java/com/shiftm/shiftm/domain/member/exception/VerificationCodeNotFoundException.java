package com.shiftm.shiftm.domain.member.exception;

import com.shiftm.shiftm.global.error.exception.EntityNotFoundException;
import com.shiftm.shiftm.global.error.exception.ErrorCode;

public class VerificationCodeNotFoundException extends EntityNotFoundException {
	public VerificationCodeNotFoundException() {
		super(ErrorCode.VERIFICATION_CODE_NOT_FOUND.getMessage(), ErrorCode.VERIFICATION_CODE_NOT_FOUND);
	}
}
