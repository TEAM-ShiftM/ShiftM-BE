package com.shiftm.shiftm.domain.leave.exception;

import com.shiftm.shiftm.global.error.exception.EntityNotFoundException;
import com.shiftm.shiftm.global.error.exception.ErrorCode;

public class LeaveNotFoundException extends EntityNotFoundException {
	public LeaveNotFoundException() {
		super(ErrorCode.LEAVE_NOT_FOUND.getMessage(), ErrorCode.LEAVE_NOT_FOUND);
	}
}
