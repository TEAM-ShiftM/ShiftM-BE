package com.shiftm.shiftm.domain.shift.exception;

import com.shiftm.shiftm.global.error.exception.EntityNotFoundException;
import com.shiftm.shiftm.global.error.exception.ErrorCode;

public class ShiftNotFoundException extends EntityNotFoundException {
	public ShiftNotFoundException() {
		super("출근 기록한 내역이 없습니다.", ErrorCode.SHIFT_NOT_FOUND);
	}
}
