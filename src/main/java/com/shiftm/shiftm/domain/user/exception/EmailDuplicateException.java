package com.shiftm.shiftm.domain.user.exception;

import com.shiftm.shiftm.global.error.exception.ErrorCode;
import com.shiftm.shiftm.global.error.exception.InvalidValueException;

public class EmailDuplicateException extends InvalidValueException {
	public EmailDuplicateException(String email) {
		super(email, ErrorCode.EMAIL_DUPLICATE);
	}
}
