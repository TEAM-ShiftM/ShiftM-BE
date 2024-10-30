package com.shiftm.shiftm.domain.leaverequest.exception;

import com.shiftm.shiftm.global.error.exception.EntityNotFoundException;
import com.shiftm.shiftm.global.error.exception.ErrorCode;

public class LeaveRequestNotFoundException extends EntityNotFoundException {
	public LeaveRequestNotFoundException() {
		super(ErrorCode.LEAVE_REQUEST_NOT_FOUND.getMessage(), ErrorCode.LEAVE_REQUEST_NOT_FOUND);
	}
}
