package com.shiftm.shiftm.domain.auth.exception;

import com.shiftm.shiftm.global.error.exception.EntityNotFoundException;

public class RefreshTokenNotFoundException extends EntityNotFoundException {
	public RefreshTokenNotFoundException(String target) {
		super(target + "Is Not Found");
	}
}
