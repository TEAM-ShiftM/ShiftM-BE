package com.shiftm.shiftm.domain.auth.exception;

import com.shiftm.shiftm.global.error.exception.ErrorCode;
import com.shiftm.shiftm.global.error.exception.InvalidValueException;

public class InvalidRefreshTokenException extends InvalidValueException {
	public InvalidRefreshTokenException() {
		super(ErrorCode.INVALID_REFRESH_TOKEN.getMessage(), ErrorCode.INVALID_REFRESH_TOKEN);
	}
}
