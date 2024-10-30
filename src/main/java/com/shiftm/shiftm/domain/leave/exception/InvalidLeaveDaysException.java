package com.shiftm.shiftm.domain.leave.exception;

import com.shiftm.shiftm.global.error.exception.ErrorCode;
import com.shiftm.shiftm.global.error.exception.InvalidValueException;

public class InvalidLeaveDaysException extends InvalidValueException {
	public InvalidLeaveDaysException(int count) {
		super(String.valueOf(count), ErrorCode.INVALID_LEAVE_DAYS);
	}
}
