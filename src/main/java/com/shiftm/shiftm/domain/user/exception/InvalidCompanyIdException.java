package com.shiftm.shiftm.domain.user.exception;

import com.shiftm.shiftm.global.error.exception.ErrorCode;
import com.shiftm.shiftm.global.error.exception.InvalidValueException;

public class InvalidCompanyIdException extends InvalidValueException {
	public InvalidCompanyIdException(String companyId) {
		super(companyId, ErrorCode.INVALID_COMPANY_ID);
	}
}
