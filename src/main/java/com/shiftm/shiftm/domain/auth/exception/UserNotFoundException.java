package com.shiftm.shiftm.domain.auth.exception;

import com.shiftm.shiftm.global.error.exception.EntityNotFoundException;

public class UserNotFoundException extends EntityNotFoundException {
	public UserNotFoundException(String target) {
		super(target + "Is Not Found");
	}
}
