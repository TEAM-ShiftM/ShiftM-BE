package com.shiftm.shiftm.domain.user.exception;

import com.shiftm.shiftm.global.error.exception.ErrorCode;
import com.shiftm.shiftm.global.error.exception.InvalidValueException;

public class InvalidPasswordException extends InvalidValueException {
	public InvalidPasswordException() {
		super(ErrorCode.INVALID_PASSWORD.getMessage(), ErrorCode.INVALID_PASSWORD);
	}
}
