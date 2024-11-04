package com.shiftm.shiftm.domain.member.exception;

import com.shiftm.shiftm.global.error.exception.EntityNotFoundException;
import com.shiftm.shiftm.global.error.exception.ErrorCode;

public class UserNotFoundException extends EntityNotFoundException {
	public UserNotFoundException(String target) {
		super(target + "Is Not Found", ErrorCode.USER_NOT_FOUND);
	}
}
