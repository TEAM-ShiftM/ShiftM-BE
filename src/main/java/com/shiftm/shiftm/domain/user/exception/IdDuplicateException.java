package com.shiftm.shiftm.domain.user.exception;

import com.shiftm.shiftm.global.error.exception.ErrorCode;
import com.shiftm.shiftm.global.error.exception.InvalidValueException;

public class IdDuplicateException extends InvalidValueException {
	public IdDuplicateException(String id) {
		super(id, ErrorCode.ID_DUPLICATE);
	}
}
